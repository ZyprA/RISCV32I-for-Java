public enum Operand {
    ADD(IType.COMP, 0, 0x0),
    SUB(IType.COMP, 0, 0x20),
    SLT(IType.COMP, 2, 0x0),
    SLTU(IType.COMP, 3, 0x0),
    AND(IType.COMP, 7, 0x0),
    OR(IType.COMP, 6, 0x0),
    XOR(IType.COMP, 4, 0x0),
    SLL(IType.COMP, 1, 0x0),
    SRL(IType.COMP, 5, 0x0),
    SRA(IType.COMP, 5, 0x20),
    ADDI(IType.COMPI, 0, 0),
    SLTI(IType.COMPI, 2, 0),
    SLTIU(IType.COMPI, 3, 0),
    ANDI(IType.COMPI, 7, 0),
    ORI(IType.COMPI, 6, 0),
    XORI(IType.COMPI, 4, 0),
    SLLI(IType.COMPI, 1, 0),
    SRLI(IType.COMPI, 5, 0),
    SRAI(IType.COMPI, 5, 0),
    LUI(IType.LUI, 0, 0),
    AUIPC(IType.AUIPC, 0, 0),
    LB(IType.LD, 0, 0),
    LBU(IType.LD, 4, 0),
    LH(IType.LD, 1, 0),
    LHU(IType.LD, 5, 0),
    LW(IType.LD, 2, 0),
    SB(IType.ST, 0, 0),
    SH(IType.ST, 1, 0),
    SW(IType.ST, 2, 0),
    JAL(IType.JAL, 0, 0),
    JALR(IType.JALR, 0, 0),
    BEQ(IType.BR, 0, 0),
    BNE(IType.BR, 1, 0),
    BLT(IType.BR, 4, 0),
    BLTU(IType.BR, 6, 0),
    BGE(IType.BR, 5, 0),
    BGEU(IType.BR, 7, 0);

    public final int funct3;
    public final int funct7;
    public int optype;

    Operand(int optype, int funct3, int funct7) {
        this.optype = optype;
        this.funct3 = funct3;
        this.funct7 = funct7;
    }
}
