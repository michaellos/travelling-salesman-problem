package com.company.entity;

import java.util.ArrayList;
import java.util.List;

public class NameConstants {

    public static final String EUC_2D_INSTANCES_PATH = "src/main/resources/instances/euc_2D/";
    public static final String EUC_2D_OPTIMAL_RESULT_PATH = "src/main/resources/optimal_result/euc_2D/";

    public static final String INSTANCE_EXTENSION = ".tsp";
    public static final String OPTIMAL_RESULT_EXTENSION = ".opt.tour";

    public static final String A280 = "a280";
    public static final String BERLIN52 = "berlin52";
    public static final String CH130 = "ch130";
    public static final String CH150 = "ch150";
    public static final String EIL101 = "eil101";
    public static final String KROA100 = "kroA100";
    public static final String KROC100 = "kroC100";
    public static final String KROD100 = "kroD100";
    public static final String LIN105 = "lin105";
    public static final String PCB442 = "pcb442";
    public static final String PR76 = "pr76";
    public static final String RD100 = "rd100";
    public static final String TSP225 = "tsp225";

    public static final List<String> INSTANCES = new ArrayList<String>() {{
        add(A280);
        add(BERLIN52);
        add(CH130);
        add(CH150);
        add(KROA100);
        add(PCB442);
        add(PR76);
        add(TSP225);
    }};
}
