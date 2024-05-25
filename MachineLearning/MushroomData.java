/**
 * @file MushroomData.java
 * 
 * @author Selepe Sello
 * @date 25 may 2024
 * @version 1.0
 * @brief Class representing data for a mushroom, including various attributes such as cap diameter, shape, gill attachment, etc.
 */

public class MushroomData {
    private final String capDiameter; ///< Diameter of the mushroom cap
    private final String capShape; ///< Shape of the mushroom cap
    private final String gillAttachment; ///< Attachment of the gills
    private final String gillColor; ///< Color of the gills
    private final String stemHeight; ///< Height of the mushroom stem
    private final String stemWidth; ///< Width of the mushroom stem
    private final String stemColor; ///< Color of the mushroom stem
    private final String season; ///< Season in which the mushroom grows
    private final String clazz; ///< Class of the mushroom (e.g., edible or poisonous)

    /**
     * @brief Constructor for MushroomData.
     * @param capDiameter Diameter of the mushroom cap.
     * @param capShape Shape of the mushroom cap.
     * @param gillAttachment Attachment of the gills.
     * @param gillColor Color of the gills.
     * @param stemHeight Height of the mushroom stem.
     * @param stemWidth Width of the mushroom stem.
     * @param stemColor Color of the mushroom stem.
     * @param season Season in which the mushroom grows.
     * @param clazz Class of the mushroom.
     */
    public MushroomData(String capDiameter, String capShape, String gillAttachment, String gillColor, String stemHeight, String stemWidth, String stemColor, String season, String clazz) {
        // Initialize cap diameter
        this.capDiameter = capDiameter;
        
        // Initialize cap shape
        this.capShape = capShape;
        
        // Initialize gill attachment
        this.gillAttachment = gillAttachment;
        
        // Initialize gill color
        this.gillColor = gillColor;
        
        // Initialize stem height
        this.stemHeight = stemHeight;
        
        // Initialize stem width
        this.stemWidth = stemWidth;
        
        // Initialize stem color
        this.stemColor = stemColor;
        
        // Initialize season
        this.season = season;
        
        // Initialize class
        this.clazz = clazz;
    }

    /**
     * @brief Gets the diameter of the mushroom cap.
     * @return Cap diameter as a string.
     */
    public String getCapDiameter() {
        return capDiameter;
    }

    /**
     * @brief Gets the shape of the mushroom cap.
     * @return Cap shape as a string.
     */
    public String getCapShape() {
        return capShape;
    }

    /**
     * @brief Gets the attachment type of the gills.
     * @return Gill attachment as a string.
     */
    public String getGillAttachment() {
        return gillAttachment;
    }

    /**
     * @brief Gets the color of the gills.
     * @return Gill color as a string.
     */
    public String getGillColor() {
        return gillColor;
    }

    /**
     * @brief Gets the height of the mushroom stem.
     * @return Stem height as a string.
     */
    public String getStemHeight() {
        return stemHeight;
    }

    /**
     * @brief Gets the width of the mushroom stem.
     * @return Stem width as a string.
     */
    public String getStemWidth() {
        return stemWidth;
    }

    /**
     * @brief Gets the color of the mushroom stem.
     * @return Stem color as a string.
     */
    public String getStemColor() {
        return stemColor;
    }

    /**
     * @brief Gets the season in which the mushroom grows.
     * @return Season as a string.
     */
    public String getSeason() {
        return season;
    }

    /**
     * @brief Gets the class of the mushroom (e.g., edible or poisonous).
     * @return Class as a string.
     */
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
