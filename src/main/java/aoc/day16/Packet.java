package aoc.day16;

import java.util.List;

record Packet(int version,
              int id,
              PacketType packetType,
              long value,
              List<Packet> subpackets) {
}
