package controller;

public class DuelWithAIController {

    private static DuelWithAIController duelWithAIController;

    private DuelWithAIController() {
    }

    public static DuelWithAIController getInstance() {
        if (duelWithAIController == null)
            duelWithAIController = new DuelWithAIController();
        return  duelWithAIController;
    }

    private int turnCounter;
    private int phase;


//    private String showCurrentMenu() {
//
//    }
//
//    public String verifyOrder(String commad) {
//
//    }
//
//    private String startGame() {
//
//    }
//
//    private String selectMonster() {
//
//    }
//
//    private String selectSpell() {
//
//    }
//
//    private String selectCard() {
//
//    }
//
//    private String summon() {
//
//    }
//
//    private String set() {
//
//    }
//
//    private String changePosition() {
//
//    }
//
//    private String flipSummon() {
//
//    }
//
//    private String attackDirect() {
//
//    }
//
//    private String acctiveEffect() {
//
//    }
//
//    private String ritualSummon() {
//
//    }
//
//    private String specialSummon() {
//
//    }
//
//    private String setTrap() {
//
//    }
//
//    private String acctivateSpell() {
//
//    }
//
//    private String activateTrap() {
//
//    }
//
//    private String showGraveyard() {
//
//    }
//
//    private String cardShow() {
//
//    }
}
