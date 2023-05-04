package mailinatorEmail;

import com.manybrain.mailinator.client.MailinatorClient;
import com.manybrain.mailinator.client.message.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class MailinatorEmail {

    public final String domain = "domain";
    public final String inbox = "local-part";

    //Connect to Mailinator
    MailinatorClient mailinatorClient = new MailinatorClient("YourTeamAPIToken");
    Document document;

    public MailinatorEmail() {
        document = getDocument();
    }
    public Document getDocument() {
        Inbox pm = mailinatorClient.request(GetInboxRequest.builder()
                .domain(domain)
                .limit(1)
                .skip(0)
                .sort(Sort.DESC)
                .build());

        //Get message
        Message message = mailinatorClient.request(new GetMessageRequest(
                domain,
                inbox,
                pm.getMsgs().get(0).getId()));
        List<Part> parts = message.getParts();

        //Pars email HTML
        return Jsoup.parse(parts.get(0).getBody());
    }
}
