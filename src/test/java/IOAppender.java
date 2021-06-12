public class IOAppender {
    public static void scoreBoardMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "scoreboard show\n" +
                "menu exit\n");
        outputStringBuilder.append("Scoreboard\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "1- AmirNick: 0\n" +
                "1- MehrNick: 0\n" +
                "1- abbas: 0\n" +
                "1- aliPalang: 0\n" +
                "1- bothRepetitive: 0\n" +
                "1- cool: 0\n" +
                "1- login: 0\n" +
                "1- mmd: 0\n" +
                "1- mmdi: 0\n" +
                "1- repetitiveNickname: 0\n" +
                "1- rezaiii: 0\n" +
                "1- rza: 0\n" +
                "1- testNickname: 0\n");
    }

    public static void duelViewInvalidRoundsIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player username --rounds 1\n" +
                "duel --new --second-player AmirAli --rounds 2\n" +
                "menu exit\n");
        outputStringBuilder.append("username's deck is invalid\r\n" +
                "number of rounds is not supported\r\n");
    }

    public static void shopViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "card show aliabad\n" +
                "card show Hero of the east\n" +
                "increase --money 0\n" +
                "shop show --all\n" +
                "shop buy not a card\n" +
                "shop buy Dark Blade\n" +
                "menu exit\n");
        outputStringBuilder.append("Shop Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "card with this name, could not be found!\r\nName: Hero of the east\n" +
                "Level: 3\nType: Normal\nATK: 1100\n" +
                "DEF: 1000\nDescription: Feel da strength ah dis sword-swinging samurai from da Far East.\r\n" +
                "your money increased by the value of 0!\r\n" +
                "Advanced Ritual Art:3000\nAlexandrite Dragon:2600\nAxe Raider:3100\nBaby dragon:1600\n" +
                "Battle OX:2900\nBattle warrior:1300\nBeast King Barbaros:9200\nBitron:1000\nBlack Pendant:4300\n" +
                "Blue-Eyes white dragon:11300\nCall of The Haunted:3500\nChange of Heart:2500\nClosed Forest:4300\n" +
                "Command Knight:2100\nCrab Turtle:10200\nCrawling dragon:3900\nCurtain of the dark ones:700\n" +
                "Dark Blade:3500\nDark Hole:2500\nDark magician:8300\nExploder Dragon:1000\nFeral Imp:2800\n" +
                "Fireyarou:2500\nFlame manipulator:1500\nForest:4300\nGate Guardian:20000\nHaniwa:600\n" +
                "Harpie's Feather Duster:2500\nHerald of Creation:2700\nHero of the east:1700\nHorn Imp:2500\n" +
                "Leotron:2500\nMagic Cylinder:2000\nMagic Jamamer:3000\nMagnum Shield:4300\nMan-Eater Bug:600\n" +
                "Marshmallon:700\nMessenger of peace:4000\nMind Crush:2000\nMirage Dragon:2500\nMirror Force:2000\n" +
                "Monster Reborn:2500\nMystical space typhoon:3500\nNegate Attack:3000\nPot of Greed:2500\n" +
                "Raigeki:2500\nRing of defense:3500\nScanner:8000\nSilver Fang:1700\nSkull Guardian:7900\n" +
                "Slot Machine:7500\nSolemn Warning:3000\nSpell Absorption:4000\nSpiral Serpent:11700\n" +
                "Suijin:8700\nSupply Squad:4000\nSword of dark destruction:4300\nSwords of Revealing Light:2500\n" +
                "Terraforming:2500\nTerratiger, the Empowered Warrior:3200\nTexchanger:200\nThe Calculator:8000\n" +
                "The Tricky:4300\nTime Seal:2000\nTorrential Tribute:2000\nTrap Hole:2000\nTwin Twisters:3500\n" +
                "Umiiruka:4300\nUnited We Stand:4300\nVanity's Emptiness:3500\nWall of Revealing Light:3500\n" +
                "Warrior Dai Grepher:3400\nWattaildragon:5800\nWattkid:1300\nYami:4300\nYomi Ship:1700\n" +
                "there is no card with this name\r\nnot enough money\r\n");
    }

    public static void loginViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "user create --username loginViewTest --nickname login --password 12345\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "user login --username loginViewTest --password 12345\n" +
                "menu exit\n" +
                "user login --password 12345 --username loginViewTest\n" +
                "menu exit\n" +
                "user login invalid command\n" +
                "menu exit\n");
        outputStringBuilder.append("Login Menu\r\n" +
                "user created successfully!\r\n" +
                "please login first\r\n" +
                "invalid command\r\n" +
                "user logged in successfully!\r\n" +
                "user logged in successfully!\r\n" +
                "invalid command\r\n");
    }

    public static void mainMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("invalid command\n" +
                "menu show-current\n" +
                "menu enter Duel\n" +
                "menu exit\n" +
                "menu enter Deck\n" +
                "menu exit\n" +
                "menu enter Shop\n" +
                "menu exit\n" + "" +
                "menu enter Scoreboard\n" +
                "menu exit\n" +
                "menu enter Profile\n" +
                "menu exit\n" +
                "menu enter Import/Export\n" +
                "\n" +
                "menu show-current\n" +
                "menu exit\n" +
                "menu enter invalidMenu\n" +
                "user logout\n" +
                "menu exit\n");
        outputStringBuilder.append("invalid command\r\n" +
                "Main Menu\r\n" +
                "invalid command\r\n" +
                "Import/Export menu\r\n" +
                "invalid command\r\n" +
                "user logged out successfully!\r\n");
    }

    public static void duelViewNoDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\n" +
                "menu enter Deck\n" +
                "menu enter Shop\n" +
                "menu enter Scoreboard\n" +
                "menu enter Profile\n" +
                "menu enter Import/Export\n" +
                "duel invalid\n" +
                "invalid\n" +
                "duel --new --second-player noSuchPlayer --rounds 1\n" +
                "duel --new --second-player username --rounds 1\n" +
                "menu exit\n");
        outputStringBuilder.append("Duel Menu\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "invalid command\r\n" +
                "there is no player with this username\r\n" +
                "repetitiveUsername has no active deck\r\n");
    }

    public static void duelViewInvalidDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player repetitiveUsername --rounds 1\n" +
                "duel --new --second-player bothRepetitive --rounds 1\n" +
                "menu exit\n");
        outputStringBuilder.append("repetitiveUsername has no active deck\r\n" +
                "username's deck is invalid\r\n");
    }

    public static void deckMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "menu enter Duel\ninvalid\ndeck invalid\ncard show  aa\n" +
                "deck create invalid deckName\ndeck create mehrDeck\ndeck create testDeck\ndeck delete i n\n" +
                "deck delete notExist\ndeck set-activate i n\ndeck set-activate notExist\n" +
                "deck set-activate testDeck\ndeck show --all\n" +
                "deck add-card --card Battle OX --deck testDeck --side\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --side --card Battle OX --deck testDeck\n" +
                "deck add-card --deck testDeck --card Command Knight --side\n" +
                "deck add-card --deck testDeck --side --card Axe Raider\n" +
                "deck add-card --side --deck testDeck --card Axe Raider\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Battle OX --side --deck test\n" +
                "deck add-card -card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Yomi Ship --deck testDeck\n" +
                "deck add-card --deck testDeck --card Yomi Ship\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck -card Yomi Ship\n" +
                "deck rm-card --card Battle OX --deck testDeck --side\n" +//
                "deck rm-card --card Battle OX --side --deck testDeck\n" +
                "deck rm-card --side --card Command Knight --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Yomi Ship --side\n" +
                "deck rm-card --deck test --side --card Battle OX\n" +
                "deck rm-card --side --deck testDeck --card aaaaa\n" +
                "deck rm-card --side --deck testDeck -card aaaaa\n" +
                "deck rm-card --card Yomi Ship --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Battle OX\n" +//
                "deck rm-card --deck test --card Battle OX\n" +
                "deck rm-card --deck testDeck -card Battle OX\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck rm-card --deck testDeck --card Scanner\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck show --deck-name testDeck --side\n" +
                "deck show --side --deck-name test\n" +
                "deck show --deck-name testDeck\n" +
                "deck show --ddeck-name testDeck\n" +
                "deck delete testDeck\n" +
                "deck set-activate mehrDeck\n" +
                "menu exit\n");
        outputStringBuilder.append("Deck Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "invalid command\r\ninvalid command\r\ninvalid command\r\ndeck with name mehrDeck already exists\r\n" +
                "deck created successfully!\r\ninvalid command\r\ndeck with name notExist does not exist\r\n" +
                "invalid command\r\ndeck with name notExist does not exist\r\ndeck activated successfully\r\n" +
                "Decks:\nActive deck:\ntestDeck: main deck 0, side deck 0, invalid\n" +
                "Other decks:\nmehrDeck: main deck 60, side deck 15, valid\n" +
                "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card with name Battle OX does not exists\r\n" +
                "deck with name test does not exist\r\n" + "invalid command\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\ncard added successfully\r\n" +
                "there are already three cards with name Scanner in deck testDeck\r\ninvalid command\r\n" +//
                "card removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" + "card removed from deck successfully\r\n" +
                "card with name Yomi Ship does not exist in side deck\r\n" + "deck with name test does not exist\r\n" +
                "card with name aaaaa does not exist in side deck\r\n" + "invalid command\r\n" + "card removed from deck successfully\r\n" +
                "card with name Battle OX does not exist in main deck\r\n" + "deck with name test does not exist\r\n" +
                "invalid command\r\ncard removed from deck successfully\r\ncard removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" +
                "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Battle OX: A monster with tremendous power, it destroys enemies with a swing of its axe.\nSpells and Traps:\n" +
                "deck with name test does not exist\n" + "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Yomi Ship: If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.\n" +
                "Spells and Traps:\n" + "invalid command\n" +
                "deck deleted successfully\r\n" +
                "deck activated successfully\r\n");
    }

    public static void duelViewAppender1(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel invalid\nduel --new --second-player AmirAli --rounds 1\n" +
                "rock\n" +
                "scissors\n" +
                "Mehrshad\n" +
                "set\n" +
                "summon\n" +
                "select --monster 5\n" +
                "select --monster 6\n" +
                "select --opponent --field\n" +
                "set --position attack\n" +
                "attack 5\n" +
                "flip-summon\n" +
                "activate effect\n" +
                "attack direct\n" +
                "select --spell 6\n" +
                "select --spell 1\n" +
                "select --hand 81\n" +
                "attack3\n" +
                "select -d\n" +
                "surrender\n" +
                "menu exit\n");
        outputStringBuilder.append("invalid command\r\n" +
                "MehrNick, please choose between Rock, Paper or Scissors:\r\n" +
                "AmirNick, please choose between Rock, Paper or Scissors:\r\n" +
                "MehrNick, please choose the first player to go: Mehrshad or AmirAli\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Mirage Dragon\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n55\n" +
                "\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n--------------------------\n\nE\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "no card found in the given position\r\n" +
                "invalid selection\r\n" +
                "no card found in the given position\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "invalid selection\r\n" +
                "no card found in the given position\r\n" +
                "invalid selection\r\n" +
                "invalid command\r\n" +
                "no card is selected yet\r\n" +
                "AmirAli won the whole match\r\n");
    }

    public static void duelViewAppender2(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel invalid\nduel --new --second-player AmirAli --rounds 1\n" +
                "rock\n" +
                "scissors\n" +
                "Mehrshad\n" +
                "next phase\n" +
                "set\n" +
                "summon\n" +
                "set --position attack\n" +
                "attack 5\n" +
                "flip-summon\n" +
                "activate effect\n" +
                "attack direct\n" +
                "attack3\n" +
                "select -d\n" +
                "next phase\n" +
                "attack\n" +
                "attack direct\n" +
                "attack 8\n" +
                "select -d\n" +
                "select --hand 4\n" +
                "card show --selected\n" +
                "activate effect\n" +
                "select --hand 7\n" +
                "card show --selected\n" +
                "summon\n" +
                "activate effect\n" +
                "show graveyard\n" +
                "show\n" +
                "invalid\n" +
                "back\n" +
                "flip-summon\n" +
                "select --hand 1\n" +
                "card show --selected\n" +
                "summon\n" +
                "yes\n" +
                "-5\n" +
                "ys\n" +
                "yes\n" +
                "hello\n" +
                "yes\n" +
                "8\n" +
                "yes\n" +
                "3\n" +
                "yes\n" +
                "2\n" +
                "yes\n" +
                "1\n" +
                "set\n" +
                "select --hand 2\n" +
                "summon\n" +
                "set\n" +
                "select --hand 1\n" +
                "set\n" +
                "set --position defence\n" +
                "select --field\n" +
                "set --position attack\n" +
                "select --monster 1\n" +
                "set --position attack\n" +
                "select --monster 2\n" +
                "set --position attack\n" +
                "activate effect\n" +
                "select -d\n" +
                "activate effect\n" +
                "next phase\n" +
                "next phase\n" +
                "select\n" +
                "flip-summon\n" +
                "attack direct\n" +
                "attack 3\n" +
                "set\n" +
                "summon\n" +
                "set --position attack\n" +
                "activate effect\n" +
                "select -d\n" +
                "show graveyard\n" +
                "back\n" +
                "card show --selected\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "special summon\n" +
                "select --hand 6\n" +
                "special summon\n" +
                "select --opponent --field\n" +
                "special summon\n" +
                "select -d\n" +
                "summon\n" +
                "select --hand 2\n" +
                "summon\n" +
                "select --hand 1\n" +
                "summon\n" +
                "select --hand 5\n" +
                "set\n" +
                "select --spell 1\n" +
                "activate effect\n" +
                "flip-summon\n" +
                "select --monster 1\n" +
                "flip-summon\n" +
                "select --hand 2\n" +
                "card show --selected\n" +
                "select --field\n" +
                "set\n" +
                "show graveyard\n" +
                "show\n" +
                "back\n" +
                "next phase\n" +
                "set\n" +
                "summon\n" +
                "flip-summon\n" +
                "activate effect\n" +
                "set --position attack\n" +
                "attack direct\n" +
                "select --monster 1\n" +
                "attack direct\n" +
                "next phase\n" +
                "select --field\n" +
                "activate effect\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --spell 1\n" +
                "activate effect\n" +
                "select --monster 1\n" +
                "flip-summon\n" +
                "select --hand 1\n" +
                "card show --selected\n" +
                "set\n" +
                "three tributes\n" +
                "set\n" +
                "two tributes\n" +
                "8\n" +
                "set\n" +
                "two tributes\n" +
                "5\n" +
                "0\n" +
                "set\n" +
                "two tributes\n" +
                "4\n" +
                "4\n" +
                "set\n" +
                "two tributes\n" +
                "1\n" +
                "3\n" +
                "set\n" +
                "no tribute\n" +
                "yes\n" +
                "select -d\n" +
                "summon\n" +
                "set\n" +
                "flip-summon\n" +
                "select\n" +
                "attack direct\n" +
                "set --position attack\n" +
                "activate effect\n" +
                "6\n" +
                "activate effect\n" +
                "2\n" +
                "activate effect\n" +
                "1\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand 1\n" +
                "special summon\n" +
                "select --hand 3\n" +
                "set\n" +
                "select --hand 3\n" +
                "set\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "next phase\n" +
                "i9\n" +
                "no\n" +
                "next phase\n" +
                "yes\n" +
                "activate effect\n" +
                "2\n" +
                "Skull Guardian\n" +
                "select --hand 1\n" +
                "summon\n" +
                "yes\n" +
                "cancel\n" +
                "next phase\n" +
                "select --field --opponent\n" +
                "attack direct\n" +
                "select --monster 1\n" +
                "attack direct\n" +
                "next phase\n" +
                "select --hand 3\n" +
                "set\n" +
                "next phase\n" +
                "next phase\n" +
                "select --hand Monster Reborn --force\n" +
                "select --force --hand Exploder Dragon\n" +
                "next phase\n" +
                "next phase\n" +
                "select --field\n" +
                "activate effect\n" +
                "select --hand 4\n" +
                "activate effect\n" +
                "10\n" +
                "1\n" +
                "7\n" +
                "ko\n" +
                "attack position\n" +
                "2\n" +
                "5\n" +
                "7\n" +
                "select --hand 3\n" +
                "activate effect\n" +
                "select --hand 3\n" +
                "summon\n" +
                "next phase\n" +
                "select --monster 2\n" +
                "attack 1\n" +
                "surrender\n" +
                "menu exit");
        outputStringBuilder.append("invalid command\r\n" +
                "MehrNick, please choose between Rock, Paper or Scissors:\r\n" +
                "AmirNick, please choose between Rock, Paper or Scissors:\r\n" +
                "MehrNick, please choose the first player to go: Mehrshad or AmirAli\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Mirage Dragon\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n55\n" +
                "\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n--------------------------\n\nE\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: standby phase\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "invalid command\r\n" +
                "no card is selected yet\r\n" +
                "phase: Main Phase 1\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "invalid command\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "no card is selected yet\r\n" +
                "card selected\r\n" +
                "Pot of Greed:Draw 2 cards.\r\n" +
                "spell activated\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t1\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "Forest:All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.\r\n" +
                "you can’t summon this card\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t1\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "spell activated\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "Pot of Greed:Draw 2 cards.\n" +
                "invalid command\r\n" +
                "no card is selected yet\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "Terratiger, the Empowered Warrior: ATK, DEF = 1800, 1200\n" +
                "When this card is Normal Summoned: You can Special Summon 1 Level 4 or lower Normal Monster from your hand in Defense Position.\r\n" +
                "do you want to activate this card effect?\r\n" +
                "Give an address\r\n" +
                "invalid command\r\n" +
                "Give an address\r\n" +
                "invalid command\r\n" +
                "Give an address\r\n" +
                "invalid command\r\n" +
                "Give an address\r\n" +
                "invalid command\r\n" +
                "Give an address\r\n" +
                "invalid command\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "do you want to activate this card effect?\r\n" +
                "Give an address\r\n" +
                "you can't special summon this card\r\n" +
                "do you want to activate this card effect?\r\n" +
                "Give an address\r\n" +
                "you can't special summon this card\r\n" +
                "do you want to activate this card effect?\r\n" +
                "Give an address\r\n" +
                "special summon of Scanner was successful\r\n" +
                "summoned successfully\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "no card is selected yet\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "you already summoned/set on this turn\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "you already summoned/set on this turn\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "set successfully\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "no card is selected yet\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "you can’t change this card position\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "this card is already in the wanted position\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "you already changed this card position in this turn\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "activate effect is only for spell cards.\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card deselected\r\n" +
                "no card is selected yet\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: Main Phase 2\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: End Phase\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "55\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "0\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "invalid command\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "no card is selected yet\r\n" +
                "no card is selected yet\r\n" +
                "its AmirNick’s turn\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Torrential Tribute\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: standby phase\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: Main Phase 1\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "no card is selected yet\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "this card can't be special summoned\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "this card can't be special summoned\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card deselected\r\n" +
                "no card is selected yet\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "this card can't be normal summoned\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "summoned successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "set successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "you can't activate trap card right now\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "you can't flip summon this card\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "you can't flip summon this card\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "E\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "Forest:All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.\r\n" +
                "no card found in the given position\r\n" +
                "set successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "graveyard empty\n" +
                "phase: battle phase\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "you can’t do this action in this phase\r\n" +
                "no card is selected yet\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "you can’t attack the opponent directly\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: Main Phase 2\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "spell activated\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: End Phase\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "52\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tDO\tOO\tE\tE\n" +
                "1\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "its MehrNick’s turn\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Crab Turtle\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t51\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: standby phase\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t51\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: Main Phase 1\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t1\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t51\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "spell activated\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "you can't flip summon this card\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "Beast King Barbaros: ATK, DEF = 3000, 1200\n" +
                "You can Normal Summon/Set this card without Tributing, but its original ATK becomes 1900. You can Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this way: Destroy all cards your opponent controls.\r\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "there is no way you could summon this monster\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "Give an address\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "Give an address\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "Give an address\r\n" +
                "Give an address\r\n" +
                "there is no monster on one of these addresses\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "0\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tOO\tDO\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "choose on option from the list below\r\n" +
                "no tribute\r\n" +
                "two tributes\r\n" +
                "three tributes\r\n" +
                "now it will be AmirAli’s turn\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tDO\tOO\tDH\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t0\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t54\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "do you want to activate your trap and spell?(yes/no)\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "it’s not your turn to play this kind of moves\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "Give an address\r\n" +
                "there is no spell or trap on this address\r\n" +
                "Give an address\r\n" +
                "summoned successfully\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: battle phase\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: Main Phase 2\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: End Phase\n" +
                "AmirNick:8000\n" +
                "\tc\tc\tc\n" +
                "54\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t49\n" +
                "\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "its AmirNick’s turn\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Mind Crush\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: standby phase\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: Main Phase 1\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "this card can't be special summoned\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "set successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\tc\n" +
                "AmirNick:8000\n" +
                "card selected\r\n" +
                "set successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: battle phase\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: Main Phase 2\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "phase: End Phase\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\n" +
                "49\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "its MehrNick’s turn\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Mind Crush\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tH\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "now it will be AmirAli’s turn\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "do you want to activate your trap and spell?(yes/no)\r\n" +
                "invalid command\r\n" +
                "phase: standby phase\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tH\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "2\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "now it will be AmirAli’s turn\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t2\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tH\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "do you want to activate your trap and spell?(yes/no)\r\n" +
                "Give an address\r\n" +
                "guess which monster does your opponent have\r\n" +
                "phase: Main Phase 1\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "now it will be AmirAli’s turn\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "6\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t3\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t53\n" +
                "\tc\tc\n" +
                "AmirNick:8000\n" +
                "do you want to activate your trap and spell?(yes/no)\r\n" +
                "summoned successfully\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: battle phase\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "you can’t attack with this card\r\n" +
                "AmirNick:8000\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "your opponent receives 1600 battle damage\r\n" +
                "AmirNick:6400\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: Main Phase 2\r\n" +
                "AmirNick:6400\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "card selected\r\n" +
                "set successfully\r\n" +
                "AmirNick:6400\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "phase: End Phase\n" +
                "AmirNick:6400\n" +
                "\tc\tc\n" +
                "53\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tE\tE\tE\n" +
                "3\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t6\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t48\n" +
                "\tc\tc\tc\n" +
                "MehrNick:8000\n" +
                "its AmirNick’s turn\r\n" +
                "phase: draw phase\r\n" +
                "new card added to the hand : Harpie's Feather Duster\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "6\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t3\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t52\n" +
                "\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "card with name Monster Reborn got added to your hand\r\n" +
                "card with name Exploder Dragon got added to your hand\r\n" +
                "phase: standby phase\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "6\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t3\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "phase: Main Phase 1\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "6\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t3\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "card selected\r\n" +
                "you have already activated this card\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "6\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t3\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "card selected\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Scanner\r\n" +
                "8: Beast King Barbaros\r\n" +
                "9: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Scanner\r\n" +
                "8: Beast King Barbaros\r\n" +
                "9: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Scanner\r\n" +
                "8: Beast King Barbaros\r\n" +
                "9: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "do you want to summon Scanner in attack position or defence position?\r\n" +
                "invalid command\r\n" +
                "do you want to summon Scanner in attack position or defence position?\r\n" +
                "summoned successfully\r\n" +
                "choose a card from graveyard to scan it's attributes for your scanner\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Beast King Barbaros\r\n" +
                "8: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "choose a card from graveyard to scan it's attributes for your scanner\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Beast King Barbaros\r\n" +
                "8: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "invalid selection\r\n" +
                "choose a card from graveyard to scan it's attributes for your scanner\r\n" +
                "1: Mirage Dragon\r\n" +
                "2: Torrential Tribute\r\n" +
                "3: Mind Crush\r\n" +
                "4: Pot of Greed\r\n" +
                "5: Pot of Greed\r\n" +
                "6: Terratiger, the Empowered Warrior\r\n" +
                "7: Beast King Barbaros\r\n" +
                "8: Skull Guardian\r\n" +
                "Give an address\r\n" +
                "spell activated\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "5\t\t\t\t\t\tO\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t4\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "card selected\r\n" +
                "spell activated\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "7\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\tc\n" +
                "AmirNick:6400\n" +
                "card selected\r\n" +
                "summoned successfully\r\n" +
                "MehrNick:8000\n" +
                "\tc\tc\tc\n" +
                "48\n" +
                "\tE\tE\tE\tE\tE\n" +
                "\tE\tE\tOO\tE\tE\n" +
                "7\t\t\t\t\t\tE\n" +
                "\n" +
                "--------------------------\n" +
                "\n" +
                "O\t\t\t\t\t\t5\n" +
                "\tE\tE\tOO\tOO\tE\n" +
                "\tE\tE\tH\tE\tE\n" +
                "\t\t\t\t\t\t50\n" +
                "\tc\tc\n" +
                "AmirNick:6400\n" +
                "Mehrshad won the whole match\r\n");
    }

    public static void profileViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" + "menu enter Duel\n" +
                "menu enter Deck\n" + "menu enter Shop\n" +
                "menu enter Scoreboard\n" + "menu enter Profile\n" +
                "menu enter Import/Export\n" +
                "profile change --nickname M k e\n" +
                "profile change --nickname AmirNick\n" +
                "profile change --nickname MehrNick2\n" +
                "profile change --nickname MehrNick\n" +
                "profile change --password --current m a --new m a\n" +
                "profile change --password --current 0000 --new 0000\n" +
                "profile change --password --current 1000 --new 0000\n" +
                "profile change --password --current 0000 --new 1111\n" +
                "profile change --current 1111 --password --new 0000\n" +
                "profile change --current 0000 --new 0000 --password\n" +
                "profile change --password --new 0000 --current 0000\n" +
                "profile change --new 0000 --password --current 0000\n" +
                "profile change --new 0000 --current 0000 --password\ninvalid command\nmenu exit\n");
        outputStringBuilder.append("Profile Menu\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "menu navigation is not possible\r\n" +
                "invalid command\r\n" +
                "user with nickname AmirNick already exists\r\n" +
                "nickname changed successfully\r\n" +
                "nickname changed successfully\r\n" +
                "invalid command\r\n" +
                "please enter a new password\r\n" +
                "current password is invalid\r\n" +
                "password changed successfully\r\n" +
                "password changed successfully\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "please enter a new password\r\n" +
                "invalid command\r\n");
    }
}
