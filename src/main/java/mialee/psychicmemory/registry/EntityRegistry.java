package mialee.psychicmemory.registry;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.game.entities.AlienEntity;
import mialee.psychicmemory.game.entities.core.BaseEntity;
import mialee.psychicmemory.game.entities.core.Entity;
import mialee.psychicmemory.lang.TranslatableText;
import org.json.JSONObject;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import static mialee.psychicmemory.PsychicMemory.LOGGER;

public class EntityRegistry {
    public static ArrayList<BaseEntity> entities = new ArrayList<>();

    public Entity alienEntity = new AlienEntity();

    public static void initialize() {
        try {
            Path path = Paths.get(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("data/entities/")).toURI());
            Stream<Path> paths = Files.walk(path);
            paths.filter(Files::isRegularFile).forEach(EntityRegistry::loadEntity);
        } catch (Exception ignored) {}
        for (BaseEntity entity : entities) {
            System.out.println(entity);
        }
    }

    public static void loadEntity(Path location) {
        try {
            JSONObject jsonObject = new JSONObject(Files.readString(location));

            String name;
            if (jsonObject.has("name")) {
                name = (String) jsonObject.get("name");
            } else {
                throw new FailedEntityLoadException();
            }
            double hitRadius;
            if (jsonObject.has("hitRadius")) {
                hitRadius = (int) jsonObject.get("hitRadius");
            } else {
                throw new FailedEntityLoadException();
            }
            int visualSize;
            if (jsonObject.has("visualSize")) {
                visualSize = (int) jsonObject.get("visualSize");
            } else {
                throw new FailedEntityLoadException();
            }
            int health;
            if (jsonObject.has("health")) {
                health = (int) jsonObject.get("health");
            } else {
                throw new FailedEntityLoadException();
            }
            ImageIcon icon;
            if (jsonObject.has("image")) {
                icon = loadImage((String) jsonObject.get("image"));
            } else {
                throw new FailedEntityLoadException();
            }

            entities.add(new BaseEntity(name, hitRadius, visualSize, health, icon));
        } catch (Exception e) {
            LOGGER.loggedError(new TranslatableText("pm.data.entity.fail"), e);
        }
    }

    public static final ImageIcon missingTexture = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/cod.png")));
    public static ImageIcon loadImage(String location) {
        ImageIcon icon;
        try {
            icon = new ImageIcon(Objects.requireNonNull(PsychicMemory.class.getClassLoader().getResource("assets/textures/" + location)));
        } catch (Exception e) {
            icon = missingTexture;
            LOGGER.loggedError(new TranslatableText("pm.data.image.missing"), location, e);
        }
        return icon;
    }
}