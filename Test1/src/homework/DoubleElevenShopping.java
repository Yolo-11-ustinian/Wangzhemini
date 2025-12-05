package homework;

import KeyboardEntry.Input;
import java.util.*;

public class DoubleElevenShopping {
    private Input input;
    private Product[] products;
    private ShoppingCart cart;
    private List<Order> orders;


    class Product {
        private int id;
        private String name;
        private double price;

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }

    }

    class CartItem {
        private Product product;
        private int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getTotalPrice() {
            return product.getPrice() * quantity;
        }

    }


    class ShoppingCart {
        private List<CartItem> items;

        public ShoppingCart() {
            this.items = new ArrayList<>();
        }

        public void addItem(Product product, int quantity) {
            for (CartItem item : items) {
                if (item.getProduct().getId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return;
                }
            }
            items.add(new CartItem(product, quantity));
        }

        public void removeItem(int productId) {
            items.removeIf(item -> item.getProduct().getId() == productId);
        }

        public void updateQuantity(int productId, int quantity) {
            for (CartItem item : items) {
                if (item.getProduct().getId() == productId) {
                    if (quantity <= 0) {
                        removeItem(productId);
                    } else {
                        item.setQuantity(quantity);
                    }
                    return;
                }
            }
        }

        public double getTotalPrice() {
            double total = 0;
            for (CartItem item : items) {
                total += item.getTotalPrice();
            }
            return total;
        }

        public List<CartItem> getItems() {
            return items;
        }

        public void clear() {
            items.clear();
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public void displayCart() {
            if (items.isEmpty()) {
                System.out.println("购物车为空");
                return;
            }

            System.out.println("\n=== 购物车内容 ===");
            System.out.println("商品名称         单价        数量  小计");
            System.out.println("----------------------------------------");
            for (CartItem item : items) {
                System.out.println(item);
            }
            System.out.println("----------------------------------------");
            System.out.printf("总价: ￥%.2f\n", getTotalPrice());
        }
    }

    class Order {
        private static int orderCounter = 1;

        private int orderId;
        private List<CartItem> items;
        private double totalPrice;
        private String orderTime;

        public Order(ShoppingCart cart) {
            this.orderId = orderCounter++;
            this.items = new ArrayList<>(cart.getItems());
            this.totalPrice = cart.getTotalPrice();
            this.orderTime = java.time.LocalDateTime.now().toString();
        }

        public void displayOrder() {
            System.out.println("\n=== 订单详情 ===");
            System.out.println("订单号: " + orderId);
            System.out.println("下单时间: " + orderTime);
            System.out.println("\n商品列表:");
            System.out.println("商品名称         单价        数量  小计");
            System.out.println("----------------------------------------");
            for (CartItem item : items) {
                System.out.println(item);
            }
            System.out.println("----------------------------------------");
            System.out.printf("订单总价: ￥%.2f\n", totalPrice);
        }
    }

    public DoubleElevenShopping() {
        this.input = new Input();
        this.cart = new ShoppingCart();
        this.orders = new ArrayList<>();
        initializeProducts();
    }

    private void initializeProducts() {
        products = new Product[] {
                new Product(1, "iPhone 17 pro max", 11999.0),
                new Product(2, "李宁羽绒服", 499.0),
                new Product(3,"Olay小白瓶",326),
                new Product(4, "iPad Air", 4399.0),
                new Product(5, "MacBook Air", 8999.0),
                new Product(6, "AirPods Pro", 1899.0),
                new Product(7, "Apple Watch", 2999.0),
                new Product(8, "OPPO Find X9", 5999.0),
                new Product(9, "十箱洁柔抽纸", 29.9),
                new Product(10, "淘淘氧棉66片", 79.9)
        };
    }

    private void displayProducts() {
        System.out.println("\n=== 双十一特价商品 ===");
        System.out.println("ID  商品名称         价格");
        System.out.println("------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("------------------------");
    }

    private void addToCart() {
        displayProducts();

        int productId = input.getInt("请输入要购买的商品ID: ", 1, products.length);
        Product selectedProduct = products[productId - 1];

        System.out.println("\n选中商品: " + selectedProduct.getName());
        System.out.printf("价格: ￥%.2f\n", selectedProduct.getPrice());

        int quantity = input.getInt("请输入购买数量: ", 1, 100); // 设置最大数量为100

        cart.addItem(selectedProduct, quantity);
        System.out.println("✓ 商品已添加到购物车！");
    }

    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("购物车为空！");
            return;
        }

        cart.displayCart();

        List<String> options = Arrays.asList(
                "修改商品数量",
                "删除商品",
                "返回主菜单"
        );

        int choice = input.getMenuChoice("购物车操作", options);

        switch (choice) {
            case 1:
                modifyCartItem();
                break;
            case 2:
                removeCartItem();
                break;
            case 3:
                break;
        }
    }

    private void modifyCartItem() {
        List<CartItem> items = cart.getItems();
        System.out.println("\n请选择要修改的商品:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, items.get(i));
        }

        int itemIndex = input.getInt("请输入商品编号: ", 1, items.size()) - 1;
        CartItem selectedItem = items.get(itemIndex);

        int newQuantity = input.getInt("请输入新的数量: ", 1, 100);
        cart.updateQuantity(selectedItem.getProduct().getId(), newQuantity);
        System.out.println("✓ 商品数量已更新！");
    }

    private void removeCartItem() {
        List<CartItem> items = cart.getItems();
        System.out.println("\n请选择要删除的商品:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, items.get(i));
        }

        int itemIndex = input.getInt("请输入商品编号: ", 1, items.size()) - 1;
        CartItem selectedItem = items.get(itemIndex);

        cart.removeItem(selectedItem.getProduct().getId());
        System.out.println("✓ 商品已从购物车删除！");
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("购物车为空，无法结账！");
            return;
        }

        cart.displayCart();

        System.out.println("\n确认结账吗？");
        List<String> options = Arrays.asList("确认结账", "取消");
        int choice = input.getMenuChoice("结账确认", options);

        if (choice == 1) {
            Order order = new Order(cart);
            orders.add(order);

            order.displayOrder();
            cart.clear();

            System.out.println("\n订单创建成功！感谢您的购买！");
        }
    }

    // 查看订单历史
    private void viewOrderHistory() {
        if (orders.isEmpty()) {
            System.out.println("暂无订单记录！");
            return;
        }

        System.out.println("\n=== 订单历史 ===");
        for (Order order : orders) {
            order.displayOrder();
            System.out.println();
        }
    }

    public void run() {
        System.out.println(" 欢迎来到双十一购物狂欢节！");

        while (true) {
            List<String> mainMenu = Arrays.asList(
                    "浏览商品",
                    "添加商品到购物车",
                    "查看购物车",
                    "结账",
                    "查看订单历史",
                    "退出系统"
            );

            int choice = input.getMenuChoice("双十一购物系统", mainMenu);

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    viewOrderHistory();
                    break;
                case 6:
                    System.out.println("感谢使用双十一购物系统，再见！");
                    return;
            }
        }
    }

    public static void main(String[] args) {
        DoubleElevenShopping shopping = new DoubleElevenShopping();
        shopping.run();
    }
}