package pl.sda.mp3analysis;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Song {
    private String artist;
    private String year;
    private String album;
    private String title;
    private String size;

    public String getArtist() {
        return "Autor: " + artist;
    }

    public String getYear() {
        return "Rok produkcji: " + year;
    }

    public String getAlbum() {
        return "Album: " + album;
    }

    public String getTitle() {
        return "Tytuł: " + title;
    }

    public String getSize() {
        return "Rozmiar w MB: " + size;
    }


    public List<Song> readDirectory(Path dir) throws IOException, InvalidDataException, UnsupportedTagException {
        List<Song> songList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{mp3}")) {
            for (Path entry : stream) {
                Mp3File mp3file = new Mp3File(entry);
                if (mp3file.hasId3v1Tag()) {
                    ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                    Song song = new Song();
                    song.artist = id3v1Tag.getArtist();
                    song.year = id3v1Tag.getYear();
                    song.album = id3v1Tag.getAlbum();
                    song.title = id3v1Tag.getTitle();
                    song.size = String.format("%.2f", (entry.toFile().length() / 1048576d));
                    songList.add(song);
                }
            }
        }
        return songList;
    }

    public void saveFile(List<Song> songList, Path dir) throws IOException {
        File file = new File(dir.toString() + "\\description.txt");

        FileWriter writer = new FileWriter(file);
        for (Song s:songList ) {
            writer.write(s.getArtist() + "\r");
            writer.write(s.getYear()+ "\r");
            writer.write(s.getAlbum()+ "\r");
            writer.write(s.getTitle()+ "\r");
            writer.write(s.getSize()+ "\r\r");
        }
        writer.close();
    }
}
