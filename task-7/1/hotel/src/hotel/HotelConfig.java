package hotel;

import java.io.*;
import java.util.Properties;

public class HotelConfig {
    private static final String PROPERTIES_FILE = "hotel_properties.prop";
    private final Properties properties;
    private int roomHistoryCount = 3;           //стандартное количество выводимых записей
    private boolean allowChangeStatus = true;   //возможность менять статус номера

    public HotelConfig() {
        this.properties = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            this.properties.load(input);
            this.allowChangeStatus = Boolean.parseBoolean(this.properties.getProperty("hotel.room.status.change.enabled", "true"));
            this.roomHistoryCount = Integer.parseInt(this.properties.getProperty("hotel.room.history.count", "3"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getAllowChangeStatus() {
        return this.allowChangeStatus;
    }

    public void setAllowChangeStatus(boolean allow) {
        this.allowChangeStatus = allow;
    }

    public int getRoomHistoryCount() {
        return this.roomHistoryCount;
    }

    public void setRoomHistoryCount(int count) {
        this.roomHistoryCount = count;
    }

    public void save() {
        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.setProperty("hotel.room.status.change.enabled", String.valueOf(this.allowChangeStatus));
            properties.setProperty("hotel.room.history.count", String.valueOf(this.roomHistoryCount));
            properties.store(output, "Hotel properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
