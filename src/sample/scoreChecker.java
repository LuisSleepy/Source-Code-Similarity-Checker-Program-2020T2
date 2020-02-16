package sample;

public class scoreChecker {
    public double xPosLeft;
    public double xPosRight;
    public double yPosTop;
    public double yPosBot;
    public double score;
    public int column;
    public int row;

    public scoreChecker(double xLeft, double xRight, double yTop, double yBot, float sc, int col,
                        int ro) {
        xPosLeft = xLeft;
        xPosRight = xRight;
        yPosTop = yTop;
        yPosBot = yBot;
        score = sc;
        column = col;
        row = ro;
    }
}
