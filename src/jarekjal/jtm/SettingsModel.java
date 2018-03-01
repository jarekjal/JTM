package jarekjal.jtm;

import jarekjal.utils.FileUtils;
import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class SettingsModel {

    private static SettingsModel instance = new SettingsModel();
    private File directory = null;
    private int number = 0;
    private Predicate<File> isMP3 = (File f) -> f.toString().toLowerCase().endsWith(".mp3");
    private List<File> fileList = null;
    private int maxNumber;
    private int minNumber;
    private boolean correct = false;

    private SettingsModel(){
    }

    public List<File> getFileList() {
        return fileList;
    }

    public static SettingsModel get(){
        return instance;
    }

    public File getDirectory() {
        return directory;
    }

    public boolean setDirectory(File directory) {
        List<File> fileList = FileUtils.flatFileTree(directory, isMP3);
        if(!fileList.isEmpty()) {
            this.directory = directory;
            this.fileList = fileList;
            maxNumber = fileList.size();
            minNumber = 1;
            setNumber(maxNumber);
            correct = true;
            return true;
        } else {
            return false;
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean setNumber(int number) {
        if (getDirectory() != null) {
            if (number <= maxNumber && number > minNumber) {
                this.number = number;
                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public boolean isCorrect() {
        return correct;
    }
}
