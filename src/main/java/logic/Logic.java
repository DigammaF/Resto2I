package logic;

import models.*;

import java.util.List;
import java.util.Optional;

/**
 *
 * Holds static methods that operate on multiple types
 *
 */
public class Logic {
    public static void addProduct(Restaurant restaurant, Product product) {
        product.setRestaurant(restaurant);
        restaurant.getProducts().add(product);
    }

    public static void updateStatementAmount(Ticket ticket, Statement statement) {
        statement.setAmount(ticket.getTotalATICost());
    }

    public static void addLiveProduct(Ticket ticket, LiveProduct liveProduct) {
        ticket.getLiveProducts().add(liveProduct);
        liveProduct.setTicket(ticket);
    }

    public static void remLiveProduct(Ticket ticket, LiveProduct liveProduct) {
        ticket.getLiveProducts().remove(liveProduct);
        liveProduct.setTicket(null);
    }

    public static void addTicket(Restaurant restaurant, Ticket ticket) {
        ticket.setRestaurant(restaurant);
        restaurant.getTickets().add(ticket);
    }

    public static void addClient(Restaurant restaurant, Client client) {
        client.setRestaurant(restaurant);
        restaurant.getClients().add(client);
    }

    public static void remClient(Restaurant restaurant, Client client) {
        client.setRestaurant(null);
        restaurant.getClients().remove(client);
    }

    public static void bindTicketStatement(Ticket ticket, Statement statement) {
        statement.setTicket(ticket);
        ticket.setStatement(statement);
    }

    public static void addStatement(Restaurant restaurant, Statement statement) {
        statement.setRestaurant(restaurant);
        restaurant.getStatements().add(statement);
    }

    @FunctionalInterface
    public interface AttributeMapper<T> {
        String select(T object);
    }

    public static <T> Optional<T> suggestBest(String text, List<T> objects, AttributeMapper<T> mapper) {
        for (T object : objects) {
            if (mapper.select(object).contains(text)) { return Optional.of(object); }
        }
        return Optional.empty();
    }
}
