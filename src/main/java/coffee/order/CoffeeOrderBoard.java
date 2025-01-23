package coffee.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CoffeeOrderBoard {
    private static final Logger logger = LoggerFactory.getLogger(CoffeeOrderBoard.class);

    private final List<Order> orders = new LinkedList<>();
    private int nextOrderNumber = 1;

    // Метод для додавання замовлення
    public void add(String customerName) {
        Order order = new Order(nextOrderNumber++, customerName);
        orders.add(order);
        logger.info("Added new order: {}", order);
    }

    // Метод для видачі наступного замовлення
    public Order deliver() {
        if (orders.isEmpty()) {
            logger.warn("Attempted to deliver an order, but the queue is empty.");
            return null;
        }
        Order order = orders.remove(0);
        logger.info("Delivered order: {}", order);
        return order;
    }

    // Перевантажений метод для видачі замовлення за номером
    public Order deliver(int orderNumber) {
        Optional<Order> orderToDeliver = orders.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst();

        if (orderToDeliver.isPresent()) {
            Order order = orderToDeliver.get();
            orders.remove(order);
            logger.info("Delivered order by number: {}", order);
            return order;
        } else {
            logger.warn("Order with number {} not found in the queue.", orderNumber);
            return null;
        }
    }

    // Метод для відображення черги
    public void draw() {
        System.out.println("Num | Name");
        orders.forEach(order ->
                System.out.println(order.getOrderNumber() + " | " + order.getCustomerName())
        );
        logger.info("Current queue displayed.");
    }
}
