package ghost;

public class Mode {
    public ModeType type;
    public int remainingFrames;
    public int totalFrames;
    public Mode(ModeType type, int seconds) {
        this.type = type;
        this.remainingFrames = seconds * 60;
        this.totalFrames = seconds * 60;
    }
}
