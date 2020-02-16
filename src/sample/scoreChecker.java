package sample;

public class scoreChecker {
    public double xPosLeft;
    public double xPosRight;
    public double score;
    public double col;
    public double row;
    public double yPosTop;
    public double yPosBot;

    public scoreChecker(double xPosLeft, double xPosRight, double YPosTop, double YPosBot, float score, double col,
                        double row) {
        this.xPosLeft = xPosLeft;
        this.xPosRight = xPosRight;
        this.score = score;
        this.col = col;
        this.row = row;
        this.yPosTop = yPosTop;
        this.yPosBot = yPosBot;
    }
}
