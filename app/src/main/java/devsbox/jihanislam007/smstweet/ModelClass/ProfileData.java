package devsbox.jihanislam007.smstweet.ModelClass;

/**
 * Created by muhmmod on 3/21/18.
 */

public class ProfileData {

    String smsId;
    String smsTitle;
    String smsBody;

    public ProfileData() {
    }

    public ProfileData(String smsId, String smsTitle, String smsBody) {
        this.smsId = smsId;
        this.smsTitle = smsTitle;
        this.smsBody = smsBody;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }
}
