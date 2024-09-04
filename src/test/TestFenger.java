package test;

import java.util.ArrayList;
import java.util.List;

public class TestFenger
{
    public void FartMain()
    {
        Packet packet = new Packet();

        //Write
        PlayerInfo playerInfo = new PlayerInfo();
        packet.write(playerInfo);

        //Read
        PlayerInfo playerInfo2 = new PlayerInfo();
        playerInfo2.read(packet);
    }

    public interface Packetable
    {
        public void write(Packet packet);
        public void read(Packet packet);
    }

    public class RoundEndData implements Packetable
    {
        public int[] playerIdRanks;
        public int[] scores;

        @Override
        public void write(Packet packet)
        {
            packet.write(playerIdRanks);
            //packet.write(scores);
        }

        @Override
        public void read(Packet packet)
        {
            playerIdRanks = packet.readIntArray();
            scores = packet.readIntArray();
        }
    }

    public class PlayerInfo implements Packetable
    {
        public String username;
        public int level;

        @Override
        public void write(Packet packet)
        {
            packet.write(username);
            packet.write(level);
        }

        @Override
        public void read(Packet packet)
        {
            username = packet.readString();
            level = packet.readInt();
        }
    }

    public class Packet
    {
        public byte[] bytes;
        private int readPos;
        static final int BufferLength = 4096;

        public Packet()
        {
            bytes = new byte[4096];
            readPos = 0;
        }

        public void write(byte b)
        {
            bytes[readPos] = b;
            readPos++;
        }

        public byte readByte()
        {
            byte b = bytes[readPos];
            readPos++;

            return b;
        }

        public void write(int v)
        {
            //Write 4 bytes
            for (int i = 0; i < 4; i++)
            {
                byte b = 0;//Nth byte of V
                write(b);
            }

            //Readpos += 4
        }

        public int readInt()
        {
            return 0;
        }

        public void write(char c)
        {

        }

        public char readChar()
        {
            return 'a';
        }

        public void write(String str)
        {
            write(str.length());

            for (int i = 0; i < str.length(); i++)
            {
                write(str.charAt(i));
            }
        }

        public String readString()
        {
            int len = readInt();

            char[] chars = new char[len];

            for (int i = 0; i < len; i++)
            {
                chars[i] = readChar();
            }

            return String.copyValueOf(chars);
        }

        public void write(Packetable packetable)
        {
            packetable.write(this);
        }

        //Arrays = {1,2,3} and {4,5,6}
        //Buffer = 3,1,2,3,3,4,5,6
        public void write(int[] array)
        {
            write(array.length);//Write Length

            for (int value : array)
            {
                write(value);//Writes each value
            }
        }

        //Array = {}
        //Buffer = 
        public int[] readIntArray()
        {
            int length = readInt();//Read Length
            int[] array = new int[length];

            for (int i = 0; i < length; i++)
            {
                array[i] = readInt();//Read N times
            }

            return array;
        }
    }    
}
