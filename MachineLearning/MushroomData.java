public class MushroomData {
    private final String capDiameter;
    private final String capShape;
    private final String gillAttachment;
    private final String gillColor;
    private final String stemHeight;
    private final String stemWidth;
    private final String stemColor;
    private final String season;
    private final String clazz;

    public MushroomData(String capDiameter, String capShape, String gillAttachment, String gillColor, String stemHeight, String stemWidth, String stemColor, String season, String clazz) {
        this.capDiameter = capDiameter;
        this.capShape = capShape;
        this.gillAttachment = gillAttachment;
        this.gillColor = gillColor;
        this.stemHeight = stemHeight;
        this.stemWidth = stemWidth;
        this.stemColor = stemColor;
        this.season = season;
        this.clazz = clazz;
    }

    public String getCapDiameter() {
        return capDiameter;
    }

    public String getCapShape() {
        return capShape;
    }

    public String getGillAttachment() {
        return gillAttachment;
    }

    public String getGillColor() {
        return gillColor;
    }

    public String getStemHeight() {
        return stemHeight;
    }

    public String getStemWidth() {
        return stemWidth;
    }

    public String getStemColor() {
        return stemColor;
    }

    public String getSeason() {
        return season;
    }

    public String getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "MushroomData{" +
                "capDiameter='" + capDiameter + '\'' +
                ", capShape='" + capShape + '\'' +
                ", gillAttachment='" + gillAttachment + '\'' +
                ", gillColor='" + gillColor + '\'' +
                ", stemHeight='" + stemHeight + '\'' +
                ", stemWidth='" + stemWidth + '\'' +
                ", stemColor='" + stemColor + '\'' +
                ", season='" + season + '\'' +
                ", clazz='" + clazz + '\'' +
                '}';
    }
}