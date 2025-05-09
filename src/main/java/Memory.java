public class Memory {
    private final byte[] mem;

    public Memory(int size) {
        mem = new byte[size];
    }

    public int loadWord(int address) {
        if (address < 0 || address + 3 >= mem.length || address % 4 != 0) {
            throw new IllegalArgumentException("Invalid address: " + address);
        }
        return ((mem[address] & 0xFF)) |
                ((mem[address + 1] & 0xFF) << 8) |
                ((mem[address + 2] & 0xFF) << 16) |
                ((mem[address + 3] & 0xFF) << 24);
    }

    public void storeWord(int address, int value) {
        if (address < 0 || address + 3 >= mem.length || address % 4 != 0) {
            throw new IllegalArgumentException("Invalid address: " + address);
        }
        mem[address] = (byte) (value & 0xFF);
        mem[address + 1] = (byte) ((value >> 8) & 0xFF);
        mem[address + 2] = (byte) ((value >> 16) & 0xFF);
        mem[address + 3] = (byte) ((value >> 24) & 0xFF);
    }

    public byte loadByte(int address) {
        return mem[address];
    }

    public void storeByte(int address, byte value) {
        mem[address] = value;
    }

    public short loadHalf(int address) {
        return (short) ((mem[address] & 0xFF) | (mem[address + 1] << 8));
    }

    public void storeHalf(int address, short value) {
        mem[address] = (byte) (value & 0xFF);
        mem[address + 1] = (byte) ((value >> 8) & 0xFF);
    }


}
