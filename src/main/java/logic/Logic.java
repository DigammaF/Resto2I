package logic;

import models.*;

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

    public static void addTicket(Ticket ticket, Restaurant restaurant) {
        ticket.setRestaurant(restaurant);
        restaurant.getTickets().add(ticket);
    }
}
