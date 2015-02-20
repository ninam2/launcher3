
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private String name;
    private String color;

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }


}