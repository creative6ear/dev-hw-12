package com.spacetravel;

import com.spacetravel.entity.Client;
import com.spacetravel.entity.Planet;
import com.spacetravel.service.ClientCrudService;
import com.spacetravel.service.PlanetCrudService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Main {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties hibernateProperties = new Properties();
            hibernateProperties.load(Main.class.getResourceAsStream("/hibernate.properties"));

            Configuration configuration = new Configuration();
            configuration.setProperties(hibernateProperties);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Planet.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static final ClientCrudService clientService = new ClientCrudService(sessionFactory);
    private static final PlanetCrudService planetService = new PlanetCrudService(sessionFactory);

    public static void main(String[] args) {
        testClientCrud();
        testPlanetCrud();
    }

    private static void testClientCrud() {
        Client client = new Client();
        client.setName("John Doe");
        clientService.addClient(client);

        Client retrievedClient = clientService.getClient(client.getId());
        System.out.println("Retrieved client: " + retrievedClient.getName());

        retrievedClient.setName("Jane Doe");
        clientService.updateClient(retrievedClient);

        clientService.deleteClient(retrievedClient.getId());
    }

    private static void testPlanetCrud() {
        Planet planet = new Planet();
        planet.setId("NEPT");
        planet.setName("Neptune");
        planetService.addPlanet(planet);

        Planet retrievedPlanet = planetService.getPlanet(planet.getId());
        System.out.println("Retrieved planet: " + retrievedPlanet.getName());

        retrievedPlanet.setName("Pluto");
        planetService.updatePlanet(retrievedPlanet);

        planetService.deletePlanet(retrievedPlanet.getId());
    }
}
