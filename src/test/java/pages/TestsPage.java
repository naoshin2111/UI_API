package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import utils.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestsPage {

    private static final List<ILabel> START_TIMES_LABELS = AqualityServices.getElementFactory().findElements(By.xpath("/html/body/div[1]/div[2]/table/tbody/tr/td[4]"), ILabel.class);

    public boolean areTestsInDescendingOrder() {

        List<Date> dates = fetchTestStartTimeFromUI()
                .stream()
                .map(DateUtils::parseStringAsDate)
                .collect(Collectors.toList());

        List<Date> sortedList = new ArrayList<>(dates);

        Collections.sort(sortedList, Collections.reverseOrder());

        return dates.equals(sortedList);
    }

    public List<String> fetchTestStartTimeFromUI() {
        return START_TIMES_LABELS
                .stream()
                .map(ILabel::getText)
                .collect(Collectors.toList());
    }

    public int startTimeColumnSize() {
        return START_TIMES_LABELS.size();
    }
}
