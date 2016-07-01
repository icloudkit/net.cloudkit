package net.cloudkit.enterprises.interfaces.member.web;

import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.*;

public final class RegistrationCommand {

    private String trackingId;

    @SuppressWarnings("unchecked")
    private List<LegCommand> legs = ListUtils.lazyList(
            new ArrayList(), LegCommand.factory()
    );

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public List<LegCommand> getLegs() {
        return legs;
    }

    public void setLegs(List<LegCommand> legs) {
        this.legs = legs;
    }

    public static final class LegCommand {

        private String voyageNumber;
        private String fromUnLocode;
        private String toUnLocode;
        private Date fromDate;
        private Date toDate;

        public String getVoyageNumber() {
            return voyageNumber;
        }

        public void setVoyageNumber(final String voyageNumber) {
            this.voyageNumber = voyageNumber;
        }

        public String getFromUnLocode() {
            return fromUnLocode;
        }

        public void setFromUnLocode(final String fromUnLocode) {
            this.fromUnLocode = fromUnLocode;
        }

        public String getToUnLocode() {
            return toUnLocode;
        }

        public void setToUnLocode(final String toUnLocode) {
            this.toUnLocode = toUnLocode;
        }

        public Date getFromDate() {
            return fromDate;
        }

        public void setFromDate(Date fromDate) {
            this.fromDate = fromDate;
        }

        public Date getToDate() {
            return toDate;
        }

        public void setToDate(Date toDate) {
            this.toDate = toDate;
        }

        public static Factory factory() {
            return new Factory() {
                public Object create() {
                    return new RegistrationCommand();
                }
            };
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // Example n°1 with a customized Factory
        {
            System.out.println("------ Example 1 with a customized Factory -------");
            Factory factory = new Factory() {
                public Object create() {
                    try{
                        Thread.currentThread().sleep(1000);
                    }catch (Throwable e) {
                        e.printStackTrace();
                    };
                    return new GregorianCalendar().getInstance();
                }
            };
            Object obj = null;

            List<Calendar> listCal = new ArrayList<Calendar>();
            System.out.println("1. listCal.size()="+listCal.size());
            listCal = ListUtils.lazyList(listCal, factory);
            System.out.println("2. listCal.size()="+listCal.size());
            //
            obj = listCal.get(3);
            System.out.println(obj instanceof GregorianCalendar);
            System.out.println(((GregorianCalendar)obj).getTime().toString());
            System.out.println("3. listCal.size()="+listCal.size());
            //
            obj = listCal.get(5);
            System.out.println(obj instanceof GregorianCalendar);
            System.out.println(((GregorianCalendar)obj).getTime().toString());
            System.out.println("4. listCal.size()="+listCal.size());
            //
            listCal.remove(1);
            System.out.println("5. listCal.size()="+listCal.size());
            listCal.remove(3);
            System.out.println("6. listCal.size()="+listCal.size());
            //
            obj = listCal.get(4);
            System.out.println(obj instanceof GregorianCalendar);
            System.out.println(((GregorianCalendar)obj).getTime().toString());
            System.out.println("7. listCal.size()="+listCal.size());
        }

        // Example n°2 with factory from Apache library
        {
            System.out.println("------ Example 2 with factory from Apache library -------");
            List<Calendar> listCal = new ArrayList<Calendar>();
            listCal = ListUtils.lazyList(listCal, FactoryUtils.instantiateFactory(GregorianCalendar.class));
            Object obj1 = listCal.get(1);
            System.out.println(obj1 instanceof GregorianCalendar);
            System.out.println(((GregorianCalendar)obj1).getTime().toString());
            //
            Object obj12 = listCal.get(12);
            System.out.println(obj12 instanceof GregorianCalendar);
            System.out.println(((GregorianCalendar)obj12).getTime().toString());
        }
    }
}


