package me.topu.api.controller;

import me.topu.api.repository.ChatFilterRepository;
import me.topu.api.repository.PlayerRepository;
import me.topu.api.repository.RankRepository;
import me.topu.api.repository.ServerGroupRepository;
import me.topu.api.model.Player;
import me.topu.api.util.PlayerUtil;
import me.topu.api.util.RankUtil;
import me.topu.api.util.ServerUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
public class ServerController {

    @Autowired private ServerGroupRepository serverGroupRepository;
    @Autowired private ChatFilterRepository chatFilterRepository;
    @Autowired private RankRepository rankRepository;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private PlayerUtil playerUtil;
    @Autowired private RankUtil rankUtil;

    @GetMapping(path = "/serverGroups")
    public ResponseEntity<List> getServerGroups(){
        List<JSONObject> serverGroups = new ArrayList<>();
        serverGroupRepository.findAll().forEach(serverGroup -> serverGroups.add(serverGroup.toJSON()));
        return new ResponseEntity<>(serverGroups, HttpStatus.OK);
    }

    @GetMapping(path = "/servers")
    public ResponseEntity<Set> getServers(){
        return new ResponseEntity<>(ServerUtil.getServersAsJSON(), HttpStatus.OK);
    }

    @PostMapping(path = "/servers/heartbeat")
    public ResponseEntity<JSONObject> serverHeartbeat(@RequestHeader(value = "MHQ-Authorization") String apiKey, @RequestBody Map<String, Object> body){
        ServerUtil.update(apiKey, body);

        Map<String, Map> onlinePlayers = (Map) body.get("players");

        JSONObject players = new JSONObject();

        onlinePlayers.entrySet().forEach((entry) -> {
            players.put(entry.getKey(), playerUtil.getPlayerByUUID(entry.getKey(), null, null));

            Player player = playerRepository.findByUuid(entry.getKey());
            player.setOnline(true);
            player.setLastSeenOn(apiKey);
            playerRepository.save(player);

            playerUtil.setOnline(entry.getKey(), apiKey);
        });

        List<Map<String, String>> events = (List) body.get("events");
        events.forEach(event -> {
            if (event.get("type").equals("leave")) {
                Player player = playerRepository.findByUuid(event.get("user"));
                player.setOnline(false);
                playerRepository.save(player);
            }
        });

        JSONObject response = new JSONObject();
        response.put("players", players);

        boolean permissionsNeeded = Boolean.parseBoolean(body.get("permissionsNeeded").toString());
        JSONObject rankPermissions = new JSONObject();
        if(permissionsNeeded) {
            rankRepository.findAll().forEach(rank -> rankPermissions.put(rank.getRankid(), rank.getPermissions()));
            response.put("permissions", rankPermissions);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/chatFilter")
    public ResponseEntity<Set> getChatFilters(){
        return new ResponseEntity<>(new HashSet<>(chatFilterRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping(path = "/whoami")
    public ResponseEntity<JSONObject> getWhoAmI(@RequestHeader(value = "MHQ-Authorization") String apiKey){
        JSONObject response = new JSONObject();
        response.put("name", apiKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/dumps/totp")
    public ResponseEntity<Set> getTotpDumps(){
        return new ResponseEntity<>(new HashSet<UUID>(), HttpStatus.OK);
    }
}