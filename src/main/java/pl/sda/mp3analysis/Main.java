package pl.sda.mp3analysis;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidDataException, UnsupportedTagException {
        Path dir = null;
        Song song = new Song();
        try {
            dir = Paths.get(args[0]);
            song.saveFile(song.readDirectory(dir), dir.toAbsolutePath());
            System.out.println("Informacje o utworach zapisano w pliku description.txt");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Nie podałeś ścieżki do pliku!");
        } catch (IOException ex) {
            System.out.println("Problem z odczytem lub zapisem!");
        }
    }
}
