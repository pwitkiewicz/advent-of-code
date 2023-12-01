package aoc.year2021.day16;

import aoc.utility.Reader;

import java.util.ArrayList;

public class Day16 {
    private static int pos;
    private static long sum = 0L;

    private static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    private static Packet decodeLiteralValuePacket(final String bin, final int version, final int id) {
        var isLast = false;
        var value = new StringBuilder();

        while(!isLast) {
            isLast = bin.charAt(pos) == '0';
            pos++;
            value.append(bin, pos, pos + 4);
            pos += 4;
        }

        sum += version;
        return new Packet(version, id, PacketType.LITERAL_VALUE, Long.parseLong(value.toString(), 2), null);
    }

    private static Packet decodeOperatorPacket(final String bin, final int version, final int id) {
        var packets = new ArrayList<Packet>();
        var lengthType = bin.charAt(pos) == '0' ? LengthType.LENGTH_IN_BITS : LengthType.NUMBER_OF_SUBPACKETS;
        pos++;
        var length = 0;
        if(lengthType == LengthType.LENGTH_IN_BITS) {
            length = Integer.parseInt(bin.substring(pos, pos+15), 2);
            pos+=15;
            var start = pos;
            while(pos < start + length) {
                packets.add(decodePacket(bin));
            }
        }
        else {
            length = Integer.parseInt(bin.substring(pos, pos+11), 2);
            pos+=11;
            for (int i = 0; i < length; i++) {
                packets.add(decodePacket(bin));
            }
        }

        sum += version;
        var value = switch (id) {
            case 0 -> packets.stream().map(Packet::value).reduce(Long::sum).orElseThrow();
            case 1 -> {
                var temp = 1L;
                for(Packet p : packets) {
                    temp *= p.value();
                }
                yield temp;
            }
            case 2 -> packets.stream().map(Packet::value).reduce(Long::min).orElseThrow();
            case 3 -> packets.stream().map(Packet::value).reduce(Long::max).orElseThrow();
            case 5 -> packets.get(0).value() > packets.get(1).value() ? 1 : 0;
            case 6 -> packets.get(0).value() < packets.get(1).value() ? 1 : 0;
            case 7 -> packets.get(0).value() == packets.get(1).value() ? 1 : 0;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };

        return new Packet(version, id, PacketType.OPERATOR, value, packets);
    }

    private static Packet decodePacket(final String bin) {
        var version = Integer.parseInt(bin.substring(pos, pos + 3), 2);
        pos += 3;
        var id = Integer.parseInt(bin.substring(pos, pos + 3), 2);
        pos += 3;

        if(id == 4) {
            return decodeLiteralValuePacket(bin, version, id);
        }

        return decodeOperatorPacket(bin, version, id);
    }

    public static void main(String[] args) {
        var hex = Reader.readLineAsString("input");
        var bin = hexToBin(hex);
        pos = 0;

        var packet = decodePacket(bin);
        System.out.println(sum);
        System.out.println(packet.value());
    }
}
