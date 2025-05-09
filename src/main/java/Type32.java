public enum Type32 {
    R_TYPE_REG(0x33),
    I_TYPE_IMM(0x13),
    U_TYPE_LUI(0x37),
    U_TYPE_AUIPC(0x17),
    I_TYPE_MEM(0x3),
    S_TYPE_MEM(0x23),
    J_TYPE_PROG(0x6f),
    I_TYPE_PROG(0x67),
    B_TYPE_PROG(0x63);

    public final int opcode;

    Type32(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public static Type32 fromOpcode(int opcode) {
        for (Type32 t : Type32.values()) {
            if (t.getOpcode() == opcode) return t;
        }
        return null;
    }
}
