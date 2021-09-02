package com.sutorimingu.no.sekai.scrapper;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sutorimingu.no.sekai.model.Anime;
import com.sutorimingu.no.sekai.model.FileDB;
import com.sutorimingu.no.sekai.repository.AnimeRepository;
import com.sutorimingu.no.sekai.service.FileStorageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author sei3
 * on 25/08/2021.
 */
@Service
public class FullScrapperToDelete {

    final static SimpleDateFormat dateFormat_Normal = new SimpleDateFormat("MMM d, yyyy", Locale.US);
    final static SimpleDateFormat dateFormat_no_Day = new SimpleDateFormat("MMM, yyyy", Locale.US);


    @Autowired
    private FileStorageService storageService;
    @Autowired
    private AnimeRepository animeRepository;


    private final static Log TRACER = LogFactory.getLog(FullScrapperToDelete.class);

    public void scrapp() {
        final String csvFile = "C:/Users/ely-b/Desktop/sutorimingu/animes.csv";
        try (final InputStream is = new FileInputStream(csvFile);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             final CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build()) {
            long nbLine = 0;
            final String[] header = csvReader.readNext();


            TRACER.info("Start processing: [" + csvFile + "]");
            while (true) {

                final String[] nextLine = csvReader.readNext();

                if (nextLine == null) {
                    TRACER.info("End of reading: [" + csvFile + "]");
                    break;
                }

                if (header.length != nextLine.length) {
                    throw new Exception("CVS malformed. Header and line column count different. Heaed " + Arrays.toString(header) + " Line " + Arrays.toString(nextLine));
                }
                FileDB store = null;
                final String imgeUrl = nextLine[10];
                if (StringUtils.isNotBlank(imgeUrl)) {
                    final String fileName = imgeUrl.substring(imgeUrl.lastIndexOf("/") + 1);
                    final byte[] imageData = recoverImageFromUrl(imgeUrl);
                    if (imageData != null) {
                        store = storageService.store(imageData, fileName);
                    }
                }
                Anime toAddAnime = new Anime();


                if (NumberUtils.isParsable(nextLine[9])) {
                    toAddAnime.setRating(Float.parseFloat(nextLine[9]));
                }
                if (store != null) {
                    toAddAnime.setFileDB(store);
                }

                final String rawDates = nextLine[4];
                final String[] dates = rawDates.split(" to ");
                if (dates.length > 1) {

                    final Date from = getDate(dates[0]);
                    toAddAnime.setAired(from);
                    final Date to = getDate(dates[1]);
                    toAddAnime.setEnded(to);

                } else {
                    final Date from = getDate(dates[0]);
                    toAddAnime.setAired(from);
                }


                toAddAnime.setTitle(nextLine[1]);
                toAddAnime.setGenre(nextLine[3]);
                toAddAnime.setSynopsis(nextLine[2]);

                animeRepository.save(toAddAnime);

                nbLine++;
                if (nbLine % 1000 == 0) {
                    if (TRACER.isDebugEnabled()) {
                        TRACER.debug("Number of anime processed : " + nbLine);
                    }
                }

            }


        } catch (Exception e) {
            TRACER.error("CA MERDE", e);
        }
    }


    public static byte[] recoverImageFromUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (Exception e) {
            return null;
        }

        return output.toByteArray();
    }

    private static java.sql.Date getDate(final String date) {
        java.util.Date from = null;
        try {
            from = dateFormat_Normal.parse(date);
        } catch (ParseException e) {
            try {
                from = dateFormat_no_Day.parse(date);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
        if (from == null) {
            return null;
        }
        return new java.sql.Date(from.getTime());
    }
}
