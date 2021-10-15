package com.zyb.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author :Z1084
 * @description :
 * @create :2021-10-15 14:21:55
 */
public class MovieTicket implements Delayed {

    private long expire;
    private String ticketNumber;

    public MovieTicket(long expire, String ticketNumber) {
        this.expire = expire + System.currentTimeMillis();
        this.ticketNumber = ticketNumber;
        System.out.println(this.expire);
        System.out.println(this.ticketNumber);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public long getExpire() {
        return expire;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "expire=" + expire +
                ", ticketNumber='" + ticketNumber + '\'' +
                '}';
    }
}
