package homework;

import KeyboardEntry.Input;
import java.util.*;
import java.util.concurrent.TimeUnit;

// 英雄基类
abstract class Hero {
    protected String name;
    protected String type;
    protected int level;
    protected int health;
    protected int maxHealth;
    protected int attack;
    protected int defense;
    protected Map<String, Long> skillCooldowns;
    protected List<Skill> skills;

    public Hero(String name, String type) {
        this.name = name;
        this.type = type;
        this.level = 1;
        this.skillCooldowns = new HashMap<>();
        this.skills = new ArrayList<>();
        initializeSkills();
    }

    protected abstract void initializeSkills();
    public abstract void levelUp();

    // 检查技能冷却
    public boolean isSkillReady(String skillName) {
        if (!skillCooldowns.containsKey(skillName)) {
            return true;
        }
        long lastUsed = skillCooldowns.get(skillName);
        long cooldown = getSkillCooldown(skillName);
        return System.currentTimeMillis() - lastUsed > cooldown;
    }

    // 使用技能
    public void useSkill(String skillName) {
        skillCooldowns.put(skillName, System.currentTimeMillis());
    }

    // 获取技能冷却时间
    private long getSkillCooldown(String skillName) {
        for (Skill skill : skills) {
            if (skill.getName().equals(skillName)) {
                return skill.getCooldown() * 1000L;
            }
        }
        return 3000;
    }

    // 获取剩余冷却时间
    public int getRemainingCooldown(String skillName) {
        if (!skillCooldowns.containsKey(skillName)) {
            return 0;
        }
        long lastUsed = skillCooldowns.get(skillName);
        long cooldown = getSkillCooldown(skillName);
        long elapsed = System.currentTimeMillis() - lastUsed;
        return (int) Math.max(0, (cooldown - elapsed) / 1000);
    }

    // 造成伤害
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }

    // 治疗
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    // 检查是否存活
    public boolean isAlive() {
        return health > 0;
    }

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public List<Skill> getSkills() { return skills; }
}

// 法师类
class Mage extends Hero {
    public Mage(String name) {
        super(name, "法师");
        this.maxHealth = 80;
        this.health = maxHealth;
        this.attack = 12;
        this.defense = 3;
    }

    @Override
    protected void initializeSkills() {
        switch (name) {
            case "妲己":
                skills.add(new Skill("失心", 8, 4, "对敌人造成法术伤害并减少魔法防御"));
                skills.add(new Skill("偶像魅力", 12, 6, "魅惑敌人并造成法术伤害"));
                skills.add(new Skill("女王崇拜", 20, 10, "释放多段狐火攻击敌人"));
                break;
            case "安琪拉":
                skills.add(new Skill("火球术", 10, 5, "发射火球造成法术伤害"));
                skills.add(new Skill("混沌火种", 15, 7, "释放火种造成范围伤害"));
                skills.add(new Skill("炽热光辉", 25, 12, "释放炽热光束持续攻击"));
                break;
            case "西施":
                skills.add(new Skill("纱缚之印", 6, 3, "标记敌人并控制其移动"));
                skills.add(new Skill("幻纱之灵", 8, 5, "释放幻纱造成法术伤害"));
                skills.add(new Skill("心无旁骛", 18, 10, "强化技能并提升控制效果"));
                break;
            case "元流之子":
                skills.add(new Skill("元气弹", 9, 4, "凝聚元气造成法术伤害"));
                skills.add(new Skill("流云步", 0, 6, "提升闪避并回复生命"));
                skills.add(new Skill("元气流光", 22, 12, "释放元气冲击波"));
                break;
        }
    }

    @Override
    public void levelUp() {
        level++;
        maxHealth += 15;
        health = maxHealth;
        attack += 3;
        defense += 1;
    }
}

// 射手类
class Archer extends Hero {
    public Archer(String name) {
        super(name, "射手");
        this.maxHealth = 70;
        this.health = maxHealth;
        this.attack = 15;
        this.defense = 2;
    }

    @Override
    protected void initializeSkills() {
        switch (name) {
            case "鲁班七号":
                skills.add(new Skill("河豚手雷", 8, 4, "投掷手雷造成物理伤害"));
                skills.add(new Skill("无敌鲨嘴炮", 14, 6, "发射火箭造成远程伤害"));
                skills.add(new Skill("空中支援", 24, 10, "召唤飞艇进行扫射"));
                break;
            case "后裔":
                skills.add(new Skill("多重箭矢", 7, 3, "同时发射多支箭矢"));
                skills.add(new Skill("惩戒射击", 16, 7, "强化普攻并减速敌人"));
                skills.add(new Skill("灼日之矢", 28, 15, "发射全图箭矢造成眩晕"));
                break;
            case "伽罗":
                skills.add(new Skill("破魔之箭", 9, 4, "穿透射击造成法术伤害"));
                skills.add(new Skill("静默之箭", 12, 6, "沉默敌人并打断技能"));
                skills.add(new Skill("纯净之域", 20, 12, "展开法阵提升攻击"));
                break;
            case "马可波罗":
                skills.add(new Skill("华丽左轮", 10, 5, "快速射击造成多段伤害"));
                skills.add(new Skill("漫游之枪", 0, 8, "位移并强化下一次攻击"));
                skills.add(new Skill("狂热弹幕", 26, 15, "旋转射击周围敌人"));
                break;
        }
    }

    @Override
    public void levelUp() {
        level++;
        maxHealth += 12;
        health = maxHealth;
        attack += 4;
        defense += 1;
    }
}

// 战士类
class Warrior extends Hero {
    public Warrior(String name) {
        super(name, "战士");
        this.maxHealth = 120;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 8;
    }

    @Override
    protected void initializeSkills() {
        switch (name) {
            case "夏侯惇":
                skills.add(new Skill("豪气斩", 12, 4, "挥砍造成物理伤害并减速"));
                skills.add(new Skill("龙卷闪", 8, 5, "旋转攻击并获得护盾"));
                skills.add(new Skill("不羁之刃", 18, 10, "冲锋并击飞敌人"));
                break;
            case "狂铁":
                skills.add(new Skill("碎裂之刃", 14, 4, "强化下一次攻击"));
                skills.add(new Skill("强袭风暴", 10, 6, "冲锋并击退敌人"));
                skills.add(new Skill("力场压制", 22, 12, "跃击造成范围伤害"));
                break;
            case "程咬金":
                skills.add(new Skill("爆裂双斧", 9, 4, "跳跃攻击并减速"));
                skills.add(new Skill("激热回旋", 7, 5, "旋转造成持续伤害"));
                skills.add(new Skill("正义潜能", 0, 30, "大幅回复生命值"));
                break;
            case "孙策":
                skills.add(new Skill("劈风斩浪", 11, 5, "冲锋并击飞敌人"));
                skills.add(new Skill("惊涛骇浪", 13, 6, "连续劈砍造成伤害"));
                skills.add(new Skill("长帆破浪", 25, 15, "驾船冲锋造成控制"));
                break;
        }
    }

    @Override
    public void levelUp() {
        level++;
        maxHealth += 25;
        health = maxHealth;
        attack += 2;
        defense += 2;
    }
}

// 技能类
class Skill {
    private String name;
    private int damage;
    private int cooldown; // 秒
    private String description;

    public Skill(String name, int damage, int cooldown, String description) {
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.description = description;
    }

    // Getters
    public String getName() { return name; }
    public int getDamage() { return damage; }
    public int getCooldown() { return cooldown; }
    public String getDescription() { return description; }
}

// 玩家类
class Player {
    private String username;
    private String password;
    private Hero hero;
    private int wins;
    private int losses;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.wins = 0;
        this.losses = 0;
    }

    // 选择英雄
    public void selectHero(String heroType, String heroName) {
        switch (heroType) {
            case "法师":
                this.hero = new Mage(heroName);
                break;
            case "射手":
                this.hero = new Archer(heroName);
                break;
            case "战士":
                this.hero = new Warrior(heroName);
                break;
        }
    }

    // 增加胜利次数
    public void addWin() {
        wins++;
        if (hero != null) {
            hero.levelUp();
        }
    }

    // 增加失败次数
    public void addLoss() {
        losses++;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Hero getHero() { return hero; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
}

// 队伍类
class Team {
    private String color;
    private List<Player> players;
    private String location;

    public Team(String color) {
        this.color = color;
        this.players = new ArrayList<>();
        this.location = "基地";
    }

    // 添加玩家到队伍
    public void addPlayer(Player player) {
        if (players.size() < 3) {
            players.add(player);
        }
    }

    // 检查队伍是否全灭
    public boolean isTeamWiped() {
        for (Player player : players) {
            if (player.getHero().isAlive()) {
                return false;
            }
        }
        return true;
    }

    // 获取存活的玩家数量
    public int getAliveCount() {
        int count = 0;
        for (Player player : players) {
            if (player.getHero().isAlive()) {
                count++;
            }
        }
        return count;
    }

    // 治疗所有存活的玩家
    public void healTeam(int amount) {
        for (Player player : players) {
            if (player.getHero().isAlive()) {
                player.getHero().heal(amount);
            }
        }
    }

    // Getters
    public String getColor() { return color; }
    public List<Player> getPlayers() { return players; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

// 游戏主类
 class GloryOfKingsMUD {
    private Input input;
    private Player currentPlayer;
    private Map<String, Player> players;
    private boolean isRunning;
    private Random random;

    // 游戏场景
    private enum Scene {
        LOGIN,
        HERO_SELECTION,
        HERO_DETAIL,
        TRAINING_GROUND,
        BATTLE_PREPARATION,
        BATTLE_FIELD
    }

    private Scene currentScene;
    private Team redTeam;
    private Team blueTeam;
    private Team currentTeam;

    // 英雄列表
    private final Map<String, List<String>> HEROES = Map.of(
            "法师", Arrays.asList("妲己", "安琪拉", "西施", "元流之子"),
            "射手", Arrays.asList("鲁班七号", "后裔", "伽罗", "马可波罗"),
            "战士", Arrays.asList("夏侯惇", "狂铁", "程咬金", "孙策")
    );

    // 英雄描述
    private final Map<String, String> HERO_DESCRIPTIONS = Map.ofEntries(
            // 法师
            Map.entry("妲己", "魅惑之狐，拥有强大的单体控制和高爆发伤害"),
            Map.entry("安琪拉", "火焰法师，擅长范围伤害和持续输出"),
            Map.entry("西施", "幻纱少女，拥有优秀的控制能力和团队辅助"),
            Map.entry("元流之子", "元气操控者，平衡的输出和生存能力"),

            // 射手
            Map.entry("鲁班七号", "机关造物，超远程输出和爆发能力"),
            Map.entry("后裔", "射日英雄，持续输出和全图支援"),
            Map.entry("伽罗", "破魔之箭，远程消耗和沉默控制"),
            Map.entry("马可波罗", "冒险枪手，灵活位移和范围扫射"),

            // 战士
            Map.entry("夏侯惇", "不羁之刃，强力控制和生存能力"),
            Map.entry("狂铁", "战锤勇士，高爆发和追击能力"),
            Map.entry("程咬金", "热血斧王，超强回复和持久战"),
            Map.entry("孙策", "江东小霸王，航海冲锋和团控")
    );

    // 战斗地点
    private final String[] BATTLE_LOCATIONS = {"中路", "蓝buff", "发育路", "对抗路", "主宰坑", "暴君坑"};

    public GloryOfKingsMUD() {
        input = new Input();
        players = new HashMap<>();
        isRunning = true;
        random = new Random();
        currentScene = Scene.LOGIN;
        redTeam = new Team("红色方");
        blueTeam = new Team("蓝色方");
    }

    // 主游戏循环
    public void run() {
        System.out.println("欢迎来到王者荣耀MUD游戏！");

        while (isRunning) {
            switch (currentScene) {
                case LOGIN:
                    loginScene();
                    break;
                case HERO_SELECTION:
                    heroSelectionScene();
                    break;
                case HERO_DETAIL:
                    heroDetailScene();
                    break;
                case TRAINING_GROUND:
                    trainingGroundScene();
                    break;
                case BATTLE_PREPARATION:
                    battlePreparationScene();
                    break;
                case BATTLE_FIELD:
                    battleFieldScene();
                    break;
            }
        }

        System.out.println("感谢游玩王者荣耀MUD游戏！");
    }

    // 登录/注册场景
    private void loginScene() {
        List<String> options = Arrays.asList(
                "注册账号",
                "登录账号",
                "退出游戏"
        );

        int choice = input.getMenuChoice("王者荣耀MUD游戏", options);

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                isRunning = false;
                break;
        }
    }

    // 注册账号
    private void register() {
        System.out.print("请输入用户名: ");
        String username = input.scanner.nextLine();

        if (players.containsKey(username)) {
            System.out.println("用户名已存在，请选择其他用户名。");
            return;
        }

        System.out.print("请输入密码: ");
        String password = input.scanner.nextLine();

        Player newPlayer = new Player(username, password);
        players.put(username, newPlayer);

        System.out.println("注册成功！");
        currentPlayer = newPlayer;
        currentScene = Scene.HERO_SELECTION;
    }

    // 登录账号
    private void login() {
        System.out.print("请输入用户名: ");
        String username = input.scanner.nextLine();

        if (!players.containsKey(username)) {
            System.out.println("用户名不存在，请先注册。");
            return;
        }

        System.out.print("请输入密码: ");
        String password = input.scanner.nextLine();

        Player player = players.get(username);
        if (player.getPassword().equals(password)) {
            currentPlayer = player;
            if (currentPlayer.getHero() == null) {
                currentScene = Scene.HERO_SELECTION;
            } else {
                currentScene = Scene.TRAINING_GROUND;
            }
            System.out.println("登录成功！欢迎回来，" + username + "！");
        } else {
            System.out.println("密码错误，请重试。");
        }
    }

    // 英雄选择场景
    private void heroSelectionScene() {
        List<String> options = Arrays.asList(
                "法师 - 高魔法伤害，生命值较低",
                "射手 - 高物理伤害，攻击速度快",
                "战士 - 高生命值，防御力强",
                "查看英雄详情",
                "返回"
        );

        int choice = input.getMenuChoice("选择英雄职业", options);

        switch (choice) {
            case 1:
                showHeroList("法师");
                break;
            case 2:
                showHeroList("射手");
                break;
            case 3:
                showHeroList("战士");
                break;
            case 4:
                currentScene = Scene.HERO_DETAIL;
                break;
            case 5:
                currentScene = Scene.LOGIN;
                break;
        }
    }

    // 显示英雄列表
    private void showHeroList(String heroType) {
        List<String> heroes = HEROES.get(heroType);
        List<String> options = new ArrayList<>();

        for (String hero : heroes) {
            options.add(hero + " - " + HERO_DESCRIPTIONS.get(hero));
        }
        options.add("返回");

        int choice = input.getMenuChoice("选择" + heroType + "英雄", options);

        if (choice <= heroes.size()) {
            String heroName = heroes.get(choice - 1);
            currentPlayer.selectHero(heroType, heroName);
            System.out.println("英雄选择成功！你的英雄是：" + heroName);
            System.out.println(HERO_DESCRIPTIONS.get(heroName));
            currentScene = Scene.TRAINING_GROUND;
        }
    }

    // 英雄详情场景
    private void heroDetailScene() {
        List<String> options = Arrays.asList(
                "查看法师英雄详情",
                "查看射手英雄详情",
                "查看战士英雄详情",
                "返回"
        );

        int choice = input.getMenuChoice("英雄详情查看", options);

        switch (choice) {
            case 1:
                showHeroDetails("法师");
                break;
            case 2:
                showHeroDetails("射手");
                break;
            case 3:
                showHeroDetails("战士");
                break;
            case 4:
                currentScene = Scene.HERO_SELECTION;
                break;
        }
    }

    // 显示英雄详情
    private void showHeroDetails(String heroType) {
        System.out.println("\n===== " + heroType + "英雄详情 =====");
        for (String heroName : HEROES.get(heroType)) {
            System.out.println("\n【" + heroName + "】");
            System.out.println("描述: " + HERO_DESCRIPTIONS.get(heroName));

            // 创建临时英雄实例来显示技能
            Hero tempHero;
            switch (heroType) {
                case "法师":
                    tempHero = new Mage(heroName);
                    break;
                case "射手":
                    tempHero = new Archer(heroName);
                    break;
                case "战士":
                    tempHero = new Warrior(heroName);
                    break;
                default:
                    continue;
            }

            System.out.println("技能:");
            for (Skill skill : tempHero.getSkills()) {
                System.out.println("  " + skill.getName() + " - 伤害:" + skill.getDamage() +
                        " 冷却:" + skill.getCooldown() + "秒");
                System.out.println("    效果: " + skill.getDescription());
            }
            System.out.println("生命值: " + tempHero.getMaxHealth() + " 攻击力: " + tempHero.getAttack() + " 防御力: " + tempHero.getDefense());
        }

        System.out.println("\n按回车键返回...");
        input.scanner.nextLine();
    }

    // 训练场场景
    private void trainingGroundScene() {
        Hero hero = currentPlayer.getHero();

        // 显示玩家信息
        System.out.println("\n===== 训练场 =====");
        System.out.println("欢迎来到训练场，" + currentPlayer.getUsername() + "！");
        System.out.println("英雄: " + hero.getName() + " (" + hero.getType() + ")");
        System.out.println("等级: " + hero.getLevel());
        System.out.println("生命值: " + hero.getHealth() + "/" + hero.getMaxHealth());
        System.out.println("攻击力: " + hero.getAttack());
        System.out.println("防御力: " + hero.getDefense());
        System.out.println("战绩: " + currentPlayer.getWins() + "胜 " + currentPlayer.getLosses() + "负");

        System.out.println("\n可用技能:");
        for (int i = 0; i < hero.getSkills().size(); i++) {
            Skill skill = hero.getSkills().get(i);
            System.out.println((i + 1) + ". " + skill.getName() + " - 伤害:" + skill.getDamage() +
                    " 冷却:" + skill.getCooldown() + "秒");
            System.out.println("   效果: " + skill.getDescription());
        }

        List<String> options = Arrays.asList(
                "练习技能",
                "进入3V3对战匹配",
                "查看英雄详情",
                "退出游戏"
        );

        int choice = input.getMenuChoice("训练场菜单", options);

        switch (choice) {
            case 1:
                practiceSkill();
                break;
            case 2:
                currentScene = Scene.BATTLE_PREPARATION;
                break;
            case 3:
                showCurrentHeroDetails();
                break;
            case 4:
                isRunning = false;
                break;
        }
    }

    // 显示当前英雄详情
    private void showCurrentHeroDetails() {
        Hero hero = currentPlayer.getHero();
        System.out.println("\n===== " + hero.getName() + " 详情 =====");
        System.out.println("职业: " + hero.getType());
        System.out.println("描述: " + HERO_DESCRIPTIONS.get(hero.getName()));
        System.out.println("等级: " + hero.getLevel());
        System.out.println("生命值: " + hero.getMaxHealth());
        System.out.println("攻击力: " + hero.getAttack());
        System.out.println("防御力: " + hero.getDefense());

        System.out.println("\n技能详情:");
        for (Skill skill : hero.getSkills()) {
            System.out.println("【" + skill.getName() + "】");
            System.out.println("  伤害: " + skill.getDamage());
            System.out.println("  冷却: " + skill.getCooldown() + "秒");
            System.out.println("  效果: " + skill.getDescription());
        }

        System.out.println("\n按回车键返回...");
        input.scanner.nextLine();
    }

    // 练习技能
    private void practiceSkill() {
        Hero hero = currentPlayer.getHero();
        List<String> skillOptions = new ArrayList<>();

        for (Skill skill : hero.getSkills()) {
            skillOptions.add(skill.getName() + " - " + skill.getDescription());
        }
        skillOptions.add("返回");

        int choice = input.getMenuChoice("选择要练习的技能", skillOptions);

        if (choice <= hero.getSkills().size()) {
            Skill skill = hero.getSkills().get(choice - 1);
            System.out.println("正在练习" + skill.getName() + "...");
            System.out.println(skill.getDescription());

            // 模拟练习时间
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("\n" + skill.getName() + " 熟练度提升！");
        }
    }

    // 战斗准备场景
    private void battlePreparationScene() {
        List<String> options = Arrays.asList(
                "红色方",
                "蓝色方"
        );

        int choice = input.getMenuChoice("3V3对战匹配 - 选择阵营", options);

        switch (choice) {
            case 1:
                redTeam.addPlayer(currentPlayer);
                currentTeam = redTeam;
                System.out.println("你已加入红色方！");
                break;
            case 2:
                blueTeam.addPlayer(currentPlayer);
                currentTeam = blueTeam;
                System.out.println("你已加入蓝色方！");
                break;
        }

        // 添加AI玩家填充队伍
        fillTeamsWithAI();

        System.out.println("\n队伍组建完成！");
        displayTeamInfo(redTeam);
        displayTeamInfo(blueTeam);

        System.out.println("\n按回车键开始战斗...");
        input.scanner.nextLine();
        currentScene = Scene.BATTLE_FIELD;
    }

    // 用AI填充队伍
    private void fillTeamsWithAI() {
        String[] aiNames = {"AI-", "AI-", "AI-", "AI-", "AI-"};

        // 填充红色方
        while (redTeam.getPlayers().size() < 3) {
            String heroType = new String[]{"法师", "射手", "战士"}[random.nextInt(3)];
            List<String> availableHeroes = HEROES.get(heroType);
            String heroName = availableHeroes.get(random.nextInt(availableHeroes.size()));

            String aiName = aiNames[redTeam.getPlayers().size()] + heroName;
            Player aiPlayer = new Player(aiName, "ai");
            aiPlayer.selectHero(heroType, heroName);
            redTeam.addPlayer(aiPlayer);
        }

        // 填充蓝色方
        while (blueTeam.getPlayers().size() < 3) {
            String heroType = new String[]{"法师", "射手", "战士"}[random.nextInt(3)];
            List<String> availableHeroes = HEROES.get(heroType);
            String heroName = availableHeroes.get(random.nextInt(availableHeroes.size()));

            String aiName = aiNames[blueTeam.getPlayers().size()] + heroName;
            Player aiPlayer = new Player(aiName, "ai");
            aiPlayer.selectHero(heroType, heroName);
            blueTeam.addPlayer(aiPlayer);
        }
    }

    // 显示队伍信息
    private void displayTeamInfo(Team team) {
        System.out.println("\n" + team.getColor() + "队伍:");
        for (int i = 0; i < team.getPlayers().size(); i++) {
            Player player = team.getPlayers().get(i);
            Hero hero = player.getHero();
            System.out.println((i + 1) + ". " + player.getUsername() + " - " +
                    hero.getName() + " (" + hero.getType() + ") Lv." + hero.getLevel());
        }
    }

    // 战场场景
    private void battleFieldScene() {
        System.out.println("\n===== 王者峡谷3V3对战开始！ =====");

        // 随机选择先手队伍
        Team attackingTeam = random.nextBoolean() ? redTeam : blueTeam;
        Team defendingTeam = (attackingTeam == redTeam) ? blueTeam : redTeam;

        int round = 1;

        // 战斗循环
        while (!redTeam.isTeamWiped() && !blueTeam.isTeamWiped()) {
            System.out.println("\n=== 第 " + round + " 回合 ===");

            // 选择战斗地点
            String battleLocation = BATTLE_LOCATIONS[random.nextInt(BATTLE_LOCATIONS.length)];
            attackingTeam.setLocation(battleLocation);
            defendingTeam.setLocation(battleLocation);

            System.out.println("📍 战斗地点: " + battleLocation);

            // 执行回合
            executeBattleRound(attackingTeam, defendingTeam);

            // 交换攻防
            Team temp = attackingTeam;
            attackingTeam = defendingTeam;
            defendingTeam = temp;

            round++;

            // 显示当前状态
            displayBattleStatus();

            // 回合间隔
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 战斗结束
        Team winningTeam = redTeam.isTeamWiped() ? blueTeam : redTeam;
        Team losingTeam = redTeam.isTeamWiped() ? redTeam : blueTeam;

        System.out.println("\n🎉===== 战斗结束！ =====");
        System.out.println("🏆 " + winningTeam.getColor() + "获得胜利！");

        // 更新战绩
        for (Player player : winningTeam.getPlayers()) {
            if (!player.getUsername().startsWith("AI")) {
                player.addWin();
            }
        }
        for (Player player : losingTeam.getPlayers()) {
            if (!player.getUsername().startsWith("AI")) {
                player.addLoss();
            }
        }

        System.out.println("\n按回车键返回训练场...");
        input.scanner.nextLine();

        // 重置队伍
        redTeam = new Team("红色方");
        blueTeam = new Team("蓝色方");
        currentScene = Scene.TRAINING_GROUND;
    }

    // 执行战斗回合
    private void executeBattleRound(Team attackingTeam, Team defendingTeam) {
        System.out.println("⚔️ " + attackingTeam.getColor() + "的回合");

        // 每个存活的玩家依次行动
        for (Player attacker : attackingTeam.getPlayers()) {
            if (!attacker.getHero().isAlive()) continue;

            // 选择目标
            Player target = selectRandomAliveTarget(defendingTeam);
            if (target == null) break; // 没有存活目标

            // 执行行动
            if (attacker.getUsername().startsWith("AI")) {
                aiAction(attacker, target);
            } else if (attacker == currentPlayer) {
                playerAction(attacker, target);
            } else {
                // 其他玩家由AI控制
                aiAction(attacker, target);
            }

            // 检查目标是否死亡
            if (!target.getHero().isAlive()) {
                System.out.println("💀 " + target.getUsername() + " 被击败了！");
            }

            // 检查战斗是否结束
            if (defendingTeam.isTeamWiped()) {
                break;
            }
        }
    }

    // 玩家行动
    private void playerAction(Player attacker, Player target) {
        Hero hero = attacker.getHero();
        System.out.println("\n🌟 " + attacker.getUsername() + " 的回合");
        System.out.println("❤️ 你的生命值: " + hero.getHealth() + "/" + hero.getMaxHealth());
        System.out.println("🎯 目标: " + target.getUsername() + " (" + target.getHero().getHealth() + "/" + target.getHero().getMaxHealth() + ")");

        List<String> actionOptions = new ArrayList<>();
        actionOptions.add("普通攻击");
        for (Skill skill : hero.getSkills()) {
            String cooldownInfo = hero.isSkillReady(skill.getName()) ? "✅就绪" : "⏰冷却中(" + hero.getRemainingCooldown(skill.getName()) + "秒)";
            actionOptions.add(skill.getName() + " - " + cooldownInfo);
        }

        int choice = input.getMenuChoice("选择行动", actionOptions);

        if (choice == 1) {
            // 普通攻击
            int damage = hero.getAttack() + random.nextInt(5);
            System.out.println("⚔️ " + attacker.getUsername() + " 对 " + target.getUsername() + " 进行普通攻击，造成 " + damage + " 点伤害！");
            target.getHero().takeDamage(damage);
        } else {
            // 使用技能
            Skill skill = hero.getSkills().get(choice - 2);
            if (hero.isSkillReady(skill.getName())) {
                int damage = skill.getDamage() + hero.getAttack() / 2;
                System.out.println("✨ " + attacker.getUsername() + " 使用 " + skill.getName() + "，对 " + target.getUsername() + " 造成 " + damage + " 点伤害！");
                System.out.println("💫 技能效果: " + skill.getDescription());
                target.getHero().takeDamage(damage);
                hero.useSkill(skill.getName());

                // 特殊技能效果
                applySkillEffects(attacker, target, skill.getName());
            } else {
                System.out.println("⏰ 技能还在冷却中！使用普通攻击代替。");
                int damage = hero.getAttack() + random.nextInt(5);
                System.out.println("⚔️ " + attacker.getUsername() + " 对 " + target.getUsername() + " 进行普通攻击，造成 " + damage + " 点伤害！");
                target.getHero().takeDamage(damage);
            }
        }
    }

    // 应用技能特效
    private void applySkillEffects(Player attacker, Player target, String skillName) {
        switch (skillName) {
            case "正义潜能": // 程咬金大招
                if (random.nextBoolean()) {
                    int healAmount = 30;
                    attacker.getHero().heal(healAmount);
                    System.out.println("💚 " + attacker.getUsername() + " 回复了 " + healAmount + " 点生命值！");
                }
                break;
            case "流云步": // 元流之子技能
                if (random.nextBoolean()) {
                    System.out.println("🌀 " + attacker.getUsername() + " 闪避了下一次攻击！");
                }
                break;
            case "灼日之矢": // 后裔大招
                if (random.nextDouble() < 0.3) {
                    System.out.println("💫 " + target.getUsername() + " 被眩晕了！");
                }
                break;
        }
    }

    // AI行动
    private void aiAction(Player attacker, Player target) {
        Hero hero = attacker.getHero();

        // AI有60%几率使用技能（如果可用）
        if (random.nextDouble() < 0.6) {
            for (Skill skill : hero.getSkills()) {
                if (hero.isSkillReady(skill.getName()) && random.nextBoolean()) {
                    int damage = skill.getDamage() + hero.getAttack() / 2;
                    System.out.println("✨ " + attacker.getUsername() + " 使用 " + skill.getName() + "，对 " + target.getUsername() + " 造成 " + damage + " 点伤害！");
                    target.getHero().takeDamage(damage);
                    hero.useSkill(skill.getName());
                    return;
                }
            }
        }

        // 使用普通攻击
        int damage = hero.getAttack() + random.nextInt(5);
        System.out.println("⚔️ " + attacker.getUsername() + " 对 " + target.getUsername() + " 进行普通攻击，造成 " + damage + " 点伤害！");
        target.getHero().takeDamage(damage);
    }

    // 选择随机存活目标
    private Player selectRandomAliveTarget(Team team) {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : team.getPlayers()) {
            if (player.getHero().isAlive()) {
                alivePlayers.add(player);
            }
        }

        if (alivePlayers.isEmpty()) {
            return null;
        }

        return alivePlayers.get(random.nextInt(alivePlayers.size()));
    }

    // 显示战斗状态
    private void displayBattleStatus() {
        System.out.println("\n----- 当前战斗状态 -----");
        System.out.println("🔴 红色方存活: " + redTeam.getAliveCount() + "/3");
        for (Player player : redTeam.getPlayers()) {
            Hero hero = player.getHero();
            System.out.println("  " + player.getUsername() + ": " + hero.getHealth() + "/" + hero.getMaxHealth() +
                    (hero.isAlive() ? " ✅存活" : " 💀死亡"));
        }

        System.out.println("🔵 蓝色方存活: " + blueTeam.getAliveCount() + "/3");
        for (Player player : blueTeam.getPlayers()) {
            Hero hero = player.getHero();
            System.out.println("  " + player.getUsername() + ": " + hero.getHealth() + "/" + hero.getMaxHealth() +
                    (hero.isAlive() ? " ✅存活" : " 💀死亡"));
        }
    }

    // 主方法
    public static void main(String[] args) {
        GloryOfKingsMUD game = new GloryOfKingsMUD();
        game.run();
    }
}
