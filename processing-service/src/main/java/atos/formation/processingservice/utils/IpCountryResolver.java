package atos.formation.processingservice.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Component
@RequiredArgsConstructor
public class IpCountryResolver {
    private DatabaseReader dbReader;

    @PostConstruct
    public void init() throws IOException {
        InputStream database = getClass().getClassLoader().getResourceAsStream("GeoLite2-Country.mmdb");
        if (database == null) {
            throw new FileNotFoundException("GeoLite2-Country.mmdb not found in classpath");
        }
        this.dbReader = new DatabaseReader.Builder(database).build();
    }

    public String getCountry(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse response = dbReader.country(ipAddress);
            return response.getCountry().getName(); // e.g., "Germany"
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
