package sample.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.Exception.ExceptionSelect;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text text;

    @FXML
    private ComboBox<String> city;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane box1;

    @FXML
    private Rectangle column1;

    @FXML
    private ImageView todayImg;

    @FXML
    private Text today;

    @FXML
    private Text todayDate;

    @FXML
    private Text day;

    @FXML
    private Text night;

    @FXML
    private Text todayTemDay;

    @FXML
    private Text todayTemNight;

    @FXML
    private Label todayVid;

    @FXML
    private AnchorPane box2;

    @FXML
    private Rectangle column11;

    @FXML
    private ImageView tomorrowImg;

    @FXML
    private Text tomorrow;

    @FXML
    private Text tomorrowDate;

    @FXML
    private Text tomorrowDay;

    @FXML
    private Text tomorrowNight;

    @FXML
    private Label tomorrowVid;

    @FXML
    private Text tomorrowTemDay;

    @FXML
    private Text tomorrowTemNight;

    @FXML
    private AnchorPane box3;

    @FXML
    private Rectangle column111;

    @FXML
    private ImageView aftertomorrowImg;

    @FXML
    private Text aftertomorrow;

    @FXML
    private Text aftertomorrowDate;

    @FXML
    private Label aftertomorrowVid;

    @FXML
    private Text aftertomorrowDay;

    @FXML
    private Text aftertomorrowNight;

    @FXML
    private Text aftertomorrowTemDay;

    @FXML
    private Text aftertomorrowTemNight;
    
private String[] parseDate(String date){
    String array[] = new String[2];
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(date);
    matcher.find();
    array[0]=date.substring(matcher.start(),matcher.end());
    pattern = Pattern.compile("[а-я]{3,}");
    matcher = pattern.matcher(date);
    matcher.find();
    array[1]= date.substring(matcher.start(),matcher.end());

return array;

}
    private void getWeather(String city) throws IOException{
        Document page = Jsoup.parse(new URL("https://meteo.by"),3000);
        Element cities = page.select("ul[class=cities]").first();
        Elements cityHref = cities.select("li");
        String href= "";
        for (Element element : cityHref) {
              if(city.equals(element.text())){
                href = new String(element.select("a").attr("href"));
            }
        }
        Document temCity = Jsoup.parse(new URL("https://meteo.by"+href),3000);
        Element weather = temCity.select("ul[class=b-weather]").first();
        Elements weatherDays = weather.select("li");
        Iterator<Element> iterator=weatherDays.iterator();
        Element element = iterator.next();
        String array[] =  parseDate(element.select("p[class=date]").text());
        todayDate.setText(array[0]+" "+array[1]);
        Element weatheNight = element.select("tr[class=time]").first();
        todayTemNight.setText(weatheNight.select("span").text());
        Elements weatheDay = element.select("tr[class=time]");
        Iterator<Element> iterator2 = weatheDay.iterator();
        iterator2.next();
        iterator2.next();
        Element element2 =iterator2.next();
        todayTemDay.setText(element2.select("span").text());
        todayImg.setImage(new Image("https://meteo.by"+element2.select("img").attr("src")));
        todayVid.setText(element2.select("td[class=icon]").text());


        element = iterator.next();
        array =  parseDate(element.select("p[class=date]").text());
        todayDate.setText(array[0]+" "+array[1]);
        weatheNight = element.select("tr[class=time]").first();
        tomorrowTemNight.setText(weatheNight.select("span").text());
        weatheDay = element.select("tr[class=time]");
        iterator2 = weatheDay.iterator();
        iterator2.next();
        iterator2.next();
        element2 =iterator2.next();
        tomorrowTemDay.setText(element2.select("span").text());
        tomorrowImg.setImage(new Image("https://meteo.by"+element2.select("img").attr("src")));
        tomorrowVid.setText(element2.select("td[class=icon]").text());

        element = iterator.next();
        array =  parseDate(element.select("p[class=date]").text());
        todayDate.setText(array[0]+" "+array[1]);
        weatheNight = element.select("tr[class=time]").first();
        aftertomorrowTemNight.setText(weatheNight.select("span").text());
        weatheDay = element.select("tr[class=time]");
        iterator2 = weatheDay.iterator();
        iterator2.next();
        iterator2.next();
        element2 =iterator2.next();
        aftertomorrowTemDay.setText(element2.select("span").text());
        aftertomorrowImg.setImage(new Image("https://meteo.by"+element2.select("img").attr("src")));
        aftertomorrowVid.setText(element2.select("td[class=icon]").text());
   }
    @FXML
    void initialize() {

city.getItems().addAll("Минск","Могилев","Гомель","Витебск","Гродно","Брест");
box1.setVisible(false);
box2.setVisible(false);
box3.setVisible(false);
tomorrowVid.setAlignment(Pos.CENTER);
todayVid.setAlignment(Pos.CENTER);
aftertomorrowVid.setAlignment(Pos.CENTER);
btn.setOnAction(event -> {
    try {
        if(city.getValue()==null){
            throw new NullPointerException();
        }
        getWeather(city.getValue());
        box1.setVisible(true);
        box2.setVisible(true);
        box3.setVisible(true);
    }catch (IOException e){
        e.printStackTrace();
    }
    catch (NullPointerException e){
        try {
            throw new ExceptionSelect();
        } catch (ExceptionSelect exceprionSelect) {
            exceprionSelect.run();
        }
    }

});
    }
}
