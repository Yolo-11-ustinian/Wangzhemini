package homework;

import java.util.Scanner;
import java.util.Random;

// 车辆基类
abstract class Vehicle {
    protected String type;
    protected double distance;

    public Vehicle(double distance) {
        this.distance = distance;
    }

    // 抽象方法，由子类实现具体的收费计算
    public abstract double calculateFee();

    // Getters
    public String getType() { return type; }
    public double getDistance() { return distance; }
}

// 小型客车
class Car extends Vehicle {
    public Car(double distance) {
        super(distance);
        this.type = "小型客车";
    }

    @Override
    public double calculateFee() {
        // 小型客车：0.5元/公里
        return distance * 0.5;
    }
}

// 大型客车
class Bus extends Vehicle {
    public Bus(double distance) {
        super(distance);
        this.type = "大型客车";
    }

    @Override
    public double calculateFee() {
        // 大型客车：0.8元/公里
        return distance * 0.8;
    }
}

// 货车
class Truck extends Vehicle {
    private double loadWeight; // 载重吨数

    public Truck(double distance, double loadWeight) {
        super(distance);
        this.type = "货车";
        this.loadWeight = loadWeight;
    }

    @Override
    public double calculateFee() {
        // 货车：基础费率 + 载重附加费
        double baseFee = distance * 0.6;
        double weightFee = loadWeight * distance * 0.1;
        return baseFee + weightFee;
    }

    public double getLoadWeight() { return loadWeight; }
}

// 摩托车
class Motorcycle extends Vehicle {
    public Motorcycle(double distance) {
        super(distance);
        this.type = "摩托车";
    }

    @Override
    public double calculateFee() {
        // 摩托车：0.3元/公里
        return distance * 0.3;
    }
}

// 收费记录类
class TollRecord {
    private Vehicle vehicle;
    private double fee;

    public TollRecord(Vehicle vehicle, double fee) {
        this.vehicle = vehicle;
        this.fee = fee;
    }

    // Getters
    public Vehicle getVehicle() { return vehicle; }
    public double getFee() { return fee; }
}

// 高速公路收费系统
public class expressway {
    private TollRecord[] records;
    private int count;
    private Scanner scanner;
    private Random random;

    public expressway(int capacity) {
        records = new TollRecord[capacity];
        count = 0;
        scanner = new Scanner(System.in);
        random = new Random();
    }

    // 添加收费记录
    public void addRecord(TollRecord record) {
        if (count < records.length) {
            records[count] = record;
            count++;
        } else {
            System.out.println("记录已满，无法添加新记录！");
        }
    }

    // 手动输入车辆信息
    public void manualInput() {
        System.out.println("\n=== 手动输入车辆信息 ===");
        System.out.println("请选择车辆类型：");
        System.out.println("1. 小型客车");
        System.out.println("2. 大型客车");
        System.out.println("3. 货车");
        System.out.println("4. 摩托车");
        System.out.print("请选择（1-4）：");

        int choice = scanner.nextInt();

        System.out.print("请输入行驶里程（公里）：");
        double distance = scanner.nextDouble();

        Vehicle vehicle = null;

        switch (choice) {
            case 1:
                vehicle = new Car(distance);
                break;
            case 2:
                vehicle = new Bus(distance);
                break;
            case 3:
                System.out.print("请输入载重吨数：");
                double loadWeight = scanner.nextDouble();
                vehicle = new Truck(distance, loadWeight);
                break;
            case 4:
                vehicle = new Motorcycle(distance);
                break;
            default:
                System.out.println("无效选择！");
                return;
        }

        processVehicle(vehicle);
    }

    // 自动生成随机车辆
    public void autoGenerate() {
        System.out.print("请输入要生成的车辆数量：");
        int num = scanner.nextInt();

        for (int i = 0; i < num; i++) {
            Vehicle vehicle = null;
            double distance = 50 + random.nextDouble() * 450; // 50-500公里

            int vehicleType = random.nextInt(4) + 1;

            switch (vehicleType) {
                case 1:
                    vehicle = new Car(distance);
                    break;
                case 2:
                    vehicle = new Bus(distance);
                    break;
                case 3:
                    double loadWeight = 1 + random.nextDouble() * 9; // 1-10吨
                    vehicle = new Truck(distance, loadWeight);
                    break;
                case 4:
                    vehicle = new Motorcycle(distance);
                    break;
            }

            processVehicle(vehicle);
        }
    }

    // 处理车辆收费
    private void processVehicle(Vehicle vehicle) {
        double fee = vehicle.calculateFee();
        TollRecord record = new TollRecord(vehicle, fee);
        addRecord(record);

        System.out.println("\n=== 收费信息 ===");
        System.out.printf("车辆类型：%s\n", vehicle.getType());
        System.out.printf("行驶里程：%.2f 公里\n", vehicle.getDistance());

        if (vehicle instanceof Truck) {
            Truck truck = (Truck) vehicle;
            System.out.printf("载重吨数：%.2f 吨\n", truck.getLoadWeight());
        }

        System.out.printf("应缴费用：%.2f 元\n", fee);
        System.out.println("================\n");
    }

    // 打印收费统计表格
    public void printStatistics() {
        if (count == 0) {
            System.out.println("暂无收费记录！");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    高速公路收费统计明细");
        System.out.println("=".repeat(70));
        System.out.printf("%-5s %-12s %-12s %-15s\n",
                "序号", "车辆类型", "里程(km)", "金额(元)");
        System.out.println("-".repeat(70));

        double totalFee = 0;
        for (int i = 0; i < count; i++) {
            TollRecord record = records[i];
            Vehicle vehicle = record.getVehicle();

            System.out.printf("%-5d %-12s %-12.2f %-15.2f\n",
                    i + 1,
                    vehicle.getType(),
                    vehicle.getDistance(),
                    record.getFee());

            totalFee += record.getFee();
        }

        System.out.println("-".repeat(70));
        System.out.printf("%-30s总计：%.2f 元\n", "", totalFee);
        System.out.printf("%-30s车辆总数：%d 辆\n", "", count);
        System.out.println("=".repeat(70));
    }

    // 显示按车辆类型的统计
    public void showTypeStatistics() {
        if (count == 0) {
            System.out.println("暂无收费记录！");
            return;
        }

        double carTotal = 0, busTotal = 0, truckTotal = 0, motorcycleTotal = 0;
        int carCount = 0, busCount = 0, truckCount = 0, motorcycleCount = 0;

        for (int i = 0; i < count; i++) {
            TollRecord record = records[i];
            Vehicle vehicle = record.getVehicle();
            double fee = record.getFee();

            switch (vehicle.getType()) {
                case "小型客车":
                    carTotal += fee;
                    carCount++;
                    break;
                case "大型客车":
                    busTotal += fee;
                    busCount++;
                    break;
                case "货车":
                    truckTotal += fee;
                    truckCount++;
                    break;
                case "摩托车":
                    motorcycleTotal += fee;
                    motorcycleCount++;
                    break;
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                按车辆类型统计");
        System.out.println("=".repeat(60));
        System.out.printf("%-12s %-10s %-15s %-15s\n", "车辆类型", "数量", "金额(元)", "占比");
        System.out.println("-".repeat(60));

        double grandTotal = carTotal + busTotal + truckTotal + motorcycleTotal;

        if (grandTotal > 0) {
            System.out.printf("%-12s %-10d %-15.2f %-15.1f%%\n",
                    "小型客车", carCount, carTotal, (carTotal / grandTotal * 100));
            System.out.printf("%-12s %-10d %-15.2f %-15.1f%%\n",
                    "大型客车", busCount, busTotal, (busTotal / grandTotal * 100));
            System.out.printf("%-12s %-10d %-15.2f %-15.1f%%\n",
                    "货车", truckCount, truckTotal, (truckTotal / grandTotal * 100));
            System.out.printf("%-12s %-10d %-15.2f %-15.1f%%\n",
                    "摩托车", motorcycleCount, motorcycleTotal, (motorcycleTotal / grandTotal * 100));
        }

        System.out.println("-".repeat(60));
        System.out.printf("%-12s %-10d %-15.2f %-15s\n",
                "总计", count, grandTotal, "100%");
        System.out.println("=".repeat(60));
    }

    // 显示菜单
    public void showMenu() {
        System.out.println("\n=== 高速公路收费系统 ===");
        System.out.println("1. 手动输入车辆信息");
        System.out.println("2. 自动生成随机车辆");
        System.out.println("3. 查看收费明细统计");
        System.out.println("4. 查看按类型统计");
        System.out.println("5. 退出系统");
        System.out.print("请选择操作（1-5）：");
    }

    // 运行系统
    public void run() {
        System.out.println("********************************************");
        System.out.println("*      欢迎使用高速公路收费仿真模拟系统       *");
        System.out.println("********************************************");

        while (true) {
            showMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manualInput();
                    break;
                case 2:
                    autoGenerate();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 4:
                    showTypeStatistics();
                    break;
                case 5:
                    System.out.println("\n感谢使用高速公路收费系统！");
                    System.out.println("退出前的收费统计：");
                    printStatistics();
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }


}