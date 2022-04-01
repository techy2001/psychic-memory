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

public class EntityRegistry<T extends Entity> {
    public static <T extends Entity> ArrayList<T> baseEntities = new ArrayList<>();
    public static final Entity alienEntity = new AlienEntity("alien", 1, 1, 1, loadImage("cod.png"));

    public static void initialize() {
        baseEntities.add(alienEntity);
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