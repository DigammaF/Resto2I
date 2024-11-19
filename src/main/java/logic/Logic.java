package logic;

import models.*;

import java.util.Date;

public class Logic {
    public static void addproduct(Restaurant restaurant, Product product) {
        product.setRestaurant(restaurant);
        restaurant.getProducts().add(product);
    }

    public static Statement emitStatement(
            Ticket ticket, Client client, Date due
    ) {
        Statement statement = new Statement();
        statement.setRestaurant(ticket.getRestaurant());
        statement.setClient(client);
        statement.setAmount(ticket.getTotalATICost());
        statement.setDue(due);
        statement.setLatePenalty(ticket.getRestaurant().getLatePenaltyPolicy());
        statement.setTicket(ticket);
        return statement;
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
