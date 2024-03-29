package me.topu.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor @Document(collection = "grants")
public class RankGrant extends Expirable {
    @Id private String id;
    @Indexed private String uuid;
    @Indexed private String reason;
    @Indexed private String rank;
    @Indexed private List<String> scopes;

    public RankGrant(String uuid, String reason, String rank, List<String> scopes, long expiresAt, long addedAt, String addedBy, String addedByIp){
        this.uuid = uuid;
        this.reason = reason;
        this.rank = rank;
        this.scopes = scopes;

        this.setExpiresIn(expiresAt);
        this.setAddedAt(addedAt);
        this.setAddedBy(addedBy);
        this.setAddedByIp(addedByIp);
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("uuid", uuid);
        json.put("reason", reason);
        json.put("rank", rank);
        json.put("scopes", scopes);
        json.put("active", this.isActive());

        return json;
    }

}
