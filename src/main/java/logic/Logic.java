package logic;

import models.*;

import java.util.Date;

public class Logic {
    public static void addproduct(Restaurant restaurant, Product product) {
        product.setRestaurant(restaurant);
        restaurant.getProducts().add(product);
    }

    public static Statement emitStatement(Ticket ticket) {
        Statement statement = new Statement();
        statement.setRestaurant(ticket.getRestaurant());
        Logic.updateStatementAmount(ticket, statement);
        statement.setDue(ticket.getDate());
        statement.setLatePenalty(ticket.getRestaurant().getLatePenaltyPolicy());
        statement.setTicket(ticket);
        return statement;
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
