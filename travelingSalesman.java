import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;
import static java.time.temporal.ChronoUnit.*;
class Destination
{
  double x;
  double y;
  String address;
  LocalDateTime orderTime;
  int index;
   
   public Destination(int index, String address, double x, double y, LocalDateTime orderTime){
      this.index = index;
      this.address = address;
      this.x=x;
      this.y=y;
      this.orderTime=orderTime;
   }
   public Destination(double x, double y){
    this.x = x; this.y = y;
   }

  //RETURNS DISTANCE IN METERS
  public double getDistanceTo(Destination B){
     return getDistance(Double.toString(x),Double.toString(y),Double.toString(B.x), Double.toString(B.y));
  }

  public double getDistance(String lt1, String ln1, String lt2, String ln2){
    final int R = 6371; // Radius of the earth
    Double lat1 = Double.parseDouble(lt1);
    Double lon1 = Double.parseDouble(ln1);
    Double lat2 = Double.parseDouble(lt2);
    Double lon2 = Double.parseDouble(ln2);
    Double latDistance = toRad(lat2-lat1);
    Double lonDistance = toRad(lon2-lon1);
    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
    Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    Double distance = R * c;
    return distance*1000;
  }

  public static Double toRad(Double value) {
       return value * Math.PI / 180;
  }

   public String toString(){
         return Integer.toString(this.index);
   }
}

class Drone
{
  static Destination origin = new Destination(0,"Burrito Bear", 53.38195, -6.59192, LocalDateTime.of(2017, 01, 01, 20,00));
  static double x;
  static double y;
  static Destination destination;
  static double mileage;
  static double lateMinutes;
  static int count;
  static final double mps = 80000/3600;
  static LocalDateTime clock;
  static ArrayList<Destination> journey;
  static ArrayList<Destination> lateDeliveries;


   public Drone()
   {
      setLocation(origin);
      this.mileage = 0;
      this.lateMinutes = 0;
      this.count = 0;
      this.clock = origin.orderTime;
      this.journey = new ArrayList<Destination>();
      this.lateDeliveries = new ArrayList<Destination>();
   }
   public void setLocation(double x, double y)
   {
      this.x = x;
      this.y = y;
      this.destination = new Destination(x,y);
   }
    public void setLocation(Destination d)
   {
      setLocation(d.x,d.y);
   }
   public void printLocation()
   {
      System.out.println(x+","+y);
   }

   public void makeDelivery(Destination d){
      double distance;
      this.journey.add(d);
      count++;
      distance = travelingSalesman.distanceDP;

      this.mileage += distance;
      this.clock = this.clock.plusSeconds(new Double(distance/mps).longValue());
      setLocation(d);

      // Illustrate Distance
      //for(int i =0; i< distance/1000; i++){System.out.print(".");}
      //  System.out.println();

      //CHECK IF DELIVERY WAS ON-TIME
      if(this.clock.isAfter(d.orderTime.plusMinutes(30))){
        double lateBy =d.orderTime.plusMinutes(30).until(clock,SECONDS)/60;
        this.lateMinutes+= lateBy;
        this.lateDeliveries.add(d);

        //System.out.println(d.index+", late by "+lateBy);
     }
   }
   public double getDistanceTo(Destination d){
      return this.destination.getDistanceTo(d);
   }
}

public class travelingSalesman{
      static double low = 100000;
     static Drone drone;
     static double distanceDP;   
      static double distanceDS;
      static double distanceSP;
      static boolean isThisPriority;
      static int x; static double y; static double z;
      static int qq;
      static double q;
      static int t;
      static int searchLateCount =0;
      //make destinations

         static final Destination delivery1 = new Destination(1,"The Paddocks, Oldtown Mill, Celbridge, Co. Kildare", 53.3473, -6.55057,LocalDateTime.of(2017, 01, 01, 19,12));
         static final Destination delivery2 = new Destination(2,"156 Glendale, Leixlip, Co. Kildare", 53.37077, -6.48279,LocalDateTime.of(2017, 01, 01, 19,12));
         static final Destination delivery3 = new Destination(3,"26 Thornhill Meadows, Celbridge, Co. Kildare", 53.35152, -6.54989,LocalDateTime.of(2017, 01, 01, 19,12));
         static final Destination delivery4 = new Destination(4,"47 Meadowbrook Avenue,Maynooth Co Kildare", 53.37278, -6.59611,LocalDateTime.of(2017, 01, 01, 19,12));
         static final Destination delivery5 = new Destination(5,"112 Mill Lane, Kilcock, Co. Kildare", 53.40126, -6.6683,LocalDateTime.of(2017, 01, 01, 19,13));
         static final Destination delivery6 = new Destination(6,"54 Willowbrook Lawns, Celbridge, Co. Kildare", 53.34484, -6.54766,LocalDateTime.of(2017, 01, 01, 19,14));
         static final Destination delivery7 = new Destination(7,"416A Ballyoulster, Celbridge, Co. Kildare", 53.34133, -6.51856,LocalDateTime.of(2017, 01, 01, 19,15));
         static final Destination delivery8 = new Destination(8,"37 The Orchard, Oldtown Mill, Celbridge, Co. Kildare", 53.34492, -6.5557,LocalDateTime.of(2017, 01, 01, 19,15));
         static final Destination delivery9 = new Destination(9,"4 Abbey Park Grove, Clane, Co. Kildare", 53.29206, -6.67685,LocalDateTime.of(2017, 01, 01, 19,16));
         static final Destination delivery10 = new Destination(10,"36 Rinawade Park, Leixlip, Co. Kildare", 53.36483, -6.51278,LocalDateTime.of(2017, 01, 01, 19,16));
         static final Destination delivery11 = new Destination(11,"1 The Close, Temple Manor,Celbridge , Co.Kildare", 53.33067, -6.54686,LocalDateTime.of(2017, 01, 01, 19,16));
         static final Destination delivery12 = new Destination(12,"35 Beech Park Wood Beech Park,Leixlip Co Kildare", 53.36287, -6.52468,LocalDateTime.of(2017, 01, 01, 19,16));
         static final Destination delivery13 = new Destination(13,"544 Riverforest, Leixlip, Co. Kildare", 53.37416, -6.49494,LocalDateTime.of(2017, 01, 01, 19,17));
         static final Destination delivery14 = new Destination(14,"54 Courtown Park,Kilcock Co Kildare", 53.39549, -6.67647,LocalDateTime.of(2017, 01, 01, 19,17));
         static final Destination delivery15 = new Destination(15,"10 The Drive, Abbeyfarm, Celbridge, Co. Kildare", 53.33239, -6.55163,LocalDateTime.of(2017, 01, 01, 19,18));
         static final Destination delivery16 = new Destination(16,"43 The Woodlands, Castletown, Celbridge, Co. Kildare", 53.34678, -6.53415,LocalDateTime.of(2017, 01, 01, 19,18));
         static final Destination delivery17 = new Destination(17,"1002 Avondale, Leixlip, Co. Kildare", 53.36869, -6.48314,LocalDateTime.of(2017, 01, 01, 19,18));
         static final Destination delivery18 = new Destination(18,"38 Parsons Hall, Maynooth, Co. Kildare", 53.37521, -6.6103,LocalDateTime.of(2017, 01, 01, 19,19));
         static final Destination delivery19 = new Destination(19,"32 The View, St.Wolstan Abbey,Celbridge, Co.Kildare", 53.33751, -6.53173,LocalDateTime.of(2017, 01, 01, 19,20));
         static final Destination delivery20 = new Destination(20,"10 Glen Easton Crescent, Leixlip, Co. Kildare", 53.37184, -6.50065, LocalDateTime.of(2017, 01, 01, 19,21));
         static final Destination delivery21 = new Destination(21,"11 Rinawade View, Leixlip, Co. Kildare", 53.36143, -6.51849, LocalDateTime.of(2017, 01, 01, 19,21));
         static final Destination delivery22 = new Destination(22,"856 Old Greenfield, Maynooth, Co.Kildare", 53.37473, -6.59338, LocalDateTime.of(2017, 01, 01, 19,21));
         static final Destination delivery23 = new Destination(23,"8 The Avenue, Castletown, Co.Kildare, Celbridge, Co. Kildare", 53.34514, -6.53615, LocalDateTime.of(2017, 01, 01, 19,22));
         static final Destination delivery24 = new Destination(24,"9 The Park, Louisa Valley, Leixlip, Co. Kildare", 53.36115, -6.48907, LocalDateTime.of(2017, 01, 01, 19,22));
         static final Destination delivery25 = new Destination(25,"33 Leinster Wood,Carton Demesne, Maynooth, Co.Kildare", 53.39351, -6.5542, LocalDateTime.of(2017, 01, 01, 19,23));
         static final Destination delivery26 = new Destination(26,"16 Priory Chase, St.Raphaels Manor,Celbridge, Co.Kildare", 53.33886, -6.55468, LocalDateTime.of(2017, 01, 01, 19,23));
         static final Destination delivery27 = new Destination(27,"14 The Rise, Louisa Valley, Leixlip, Co. Kildare", 53.36115, -6.48907, LocalDateTime.of(2017, 01, 01, 19,25));
         static final Destination delivery28 = new Destination(28,"646 Riverforest,Leixlip Co Kildare", 53.37497, -6.4991, LocalDateTime.of(2017, 01, 01, 19,26));
         static final Destination delivery29 = new Destination(29,"76 Castle Dawson, Maynooth, Co. Kildare", 53.37565, -6.60716, LocalDateTime.of(2017, 01, 01, 19,27));
         static final Destination delivery30 = new Destination(30,"23 Priory Way, St.Raphaels Manor,,Celbridge, Co.Kildare", 53.3354, -6.55111, LocalDateTime.of(2017, 01, 01, 19,27));
         static final Destination delivery31 = new Destination(31,"5 Rinawade View, Leixlip, Co. Kildare", 53.36143, -6.51849, LocalDateTime.of(2017, 01, 01, 19,28));
         static final Destination delivery32 = new Destination(32,"11 The Park, Louisa Valley, Leixlip, Co. Kildare", 53.36115, -6.48907, LocalDateTime.of(2017, 01, 01, 19,28));
         static final Destination delivery33 = new Destination(33,"117 Royal Meadows,Kilcock Co Kildare", 53.39459, -6.66995, LocalDateTime.of(2017, 01, 01, 19,28));
         static final Destination delivery34 = new Destination(34,"12 Maynooth Park, Maynooth, Co. Kildare", 53.37122, -6.586, LocalDateTime.of(2017, 01, 01, 19,29));
         static final Destination delivery35 = new Destination(35,"30 Ryevale Lawns, Leixlip, Co. Kildare", 53.36656, -6.49183, LocalDateTime.of(2017, 01, 01, 19,30));
         static final Destination delivery36 = new Destination(36,"44 Rinawade Avenue, Leixlip, Co. Kildare", 53.36141, -6.51834, LocalDateTime.of(2017, 01, 01, 19,30));
         static final Destination delivery37 = new Destination(37,"7 Straffan Green, Straffan Wood, Maynooth, Co. Kildare", 53.37323, -6.58859, LocalDateTime.of(2017, 01, 01, 19,30));
         static final Destination delivery38 = new Destination(38,"29 Castletown, Leixlip, Co. Kildare", 53.36292, -6.50203, LocalDateTime.of(2017, 01, 01, 19,31));
         static final Destination delivery39 = new Destination(39,"1 Kyldar House, Manor Mills, Maynooth, Co. Kildare", 53.38122, -6.59226, LocalDateTime.of(2017, 01, 01, 19,31));
         static final Destination delivery40 = new Destination(40,"83 Thornhill Meadows, Celbridge, Co. Kildare", 53.35098, -6.54915, LocalDateTime.of(2017, 01, 01, 19,31)); 
         static final Destination delivery41 = new Destination(41,"90 Vanessa Lawns, Celbridge, Co. Kildare", 53.34312, -6.54747, LocalDateTime.of(2017, 01, 01, 19,31));
         static final Destination delivery42 = new Destination(42,"50 The Lawn, Oldtown Mill, Celbridge, Co. Kildare", 53.34197, -6.55492, LocalDateTime.of(2017, 01, 01, 19,31));
         static final Destination delivery43 = new Destination(43,"20 Habourview, The Glenroyal Centre, Maynooth, Co.Kildare", 53.37954, -6.58793, LocalDateTime.of(2017, 01, 01, 19,32));
         static final Destination delivery44 = new Destination(44,"13 The Little Grove, Celbridge, Co Kildare", 53.33835, -6.53984, LocalDateTime.of(2017, 01, 01, 19,32));
         static final Destination delivery45 = new Destination(45,"10 Brookfield Avenue,Maynooth Co Kildare", 53.36976, -6.59828, LocalDateTime.of(2017, 01, 01, 19,33));
         static final Destination delivery46 = new Destination(46,"35 Rail Park, Co.Kildare, Maynooth, Co. Kildare", 53.37811, -6.57952, LocalDateTime.of(2017, 01, 01, 19,34));
         static final Destination delivery47 = new Destination(47,"10 Fair Green Court, Kilccock,, Co. Kildare", 53.39847, -6.66787, LocalDateTime.of(2017, 01, 01, 19,34));
         static final Destination delivery48 = new Destination(48,"3 Lyreen Park,Maynooth Co Kildare", 53.38579, -6.58673, LocalDateTime.of(2017, 01, 01, 19,34));
         static final Destination delivery49 = new Destination(49,"34 Silken Vale, Maynooth, Co. Kildare", 53.37626, -6.59308, LocalDateTime.of(2017, 01, 01, 19,34));
         static final Destination delivery50 = new Destination(50,"35 Glen Easton Square, Leixlip, Co. Kildare", 53.37336, -6.48219, LocalDateTime.of(2017, 01, 01, 19,35));
         static final Destination delivery51 = new Destination(51,"10 The Court, Abbey Farm,,Celbridge, Co.Kildare", 53.33032, -6.55311, LocalDateTime.of(2017, 01, 01, 19,35));
         static final Destination delivery52 = new Destination(52,"4 Glendale, Leixlip, Co. Kildare", 53.37201, -6.48517, LocalDateTime.of(2017, 01, 01, 19,36));
         static final Destination delivery53 = new Destination(53,"628 Riverforest, Leixlip, Co. Kildare", 53.37416, -6.49731, LocalDateTime.of(2017, 01, 01, 19,36));
         static final Destination delivery54 = new Destination(54,"111 Elton Court, Leixlip, Co. Kildare", 53.36164, -6.50526, LocalDateTime.of(2017, 01, 01, 19,36));
         static final Destination delivery55 = new Destination(55,"169 Glendale, Co.Kildare, Leixlip, Co. Kildare", 53.37043, -6.48193, LocalDateTime.of(2017, 01, 01, 19,37));
         static final Destination delivery56 = new Destination(56,"94 Croduan Forest Park, Celbridge, Co. Kildare", 53.35372, -6.54564, LocalDateTime.of(2017, 01, 01, 19,37));
         static final Destination delivery57 = new Destination(57,"13 Abbey Park Court , Clane, , Co Kildare.", 53.2908, -6.67746, LocalDateTime.of(2017, 01, 01, 19,37));
         static final Destination delivery58 = new Destination(58,"533 Courtown Road, Kilcock, Co. Kildare", 53.39792, -6.6711, LocalDateTime.of(2017, 01, 01, 19,39));
         static final Destination delivery59 = new Destination(59,"13 The Hawthorns, Kilcock, Co. Kildare", 53.39315, -6.66909, LocalDateTime.of(2017, 01, 01, 19,41));
         static final Destination delivery60 = new Destination(60,"106 The Drive, Castletown,,Celbridge, Co.Kildare", 53.34439, -6.53841, LocalDateTime.of(2017, 01, 01, 19,41));
         static final Destination delivery61 = new Destination(61,"15 Willow Rise, Primrose Gate, Celbridge, Co. Kildare", 53.33591, -6.53566, LocalDateTime.of(2017, 01, 01, 19,42));
         static final Destination delivery62 = new Destination(62,"7 Rinawade Park, Leixlip, Co. Kildare", 53.3632, -6.51178, LocalDateTime.of(2017, 01, 01, 19,42));
         static final Destination delivery63 = new Destination(63,"40 Oaklawn West., Leixlip, Co. Kildare", 53.36833, -6.50589, LocalDateTime.of(2017, 01, 01, 19,43));
         static final Destination delivery64 = new Destination(64,"12 Castlevillage Avenue, Celbridge, Co. Kildare", 53.35298, -6.54921, LocalDateTime.of(2017, 01, 01, 19,43));
         static final Destination delivery65 = new Destination(65,"107 Castle Dawson, Maynooth, Co. Kildare", 53.38122, -6.59226, LocalDateTime.of(2017, 01, 01, 19,43));
         static final Destination delivery66 = new Destination(66,"The Downs, St.Wolstan Abbey,,Celbridge, Co.Kildare", 53.33605, -6.53414, LocalDateTime.of(2017, 01, 01, 19,43));
         static final Destination delivery67 = new Destination(67,"Simmonstown Manor, Celbridge, Co. Kildare", 53.33324, -6.53978, LocalDateTime.of(2017, 01, 01, 19,45));
         static final Destination delivery68 = new Destination(68,"2 Parsons Street, Maynooth, Co. Kildare", 53.38039, -6.59368, LocalDateTime.of(2017, 01, 01, 19,45));
         static final Destination delivery69 = new Destination(69,"6 Glen Easton View,Leixlip Co Kildare", 53.36883, -6.51468, LocalDateTime.of(2017, 01, 01, 19,45));
         static final Destination delivery70 = new Destination(70,"78 Crodaun Forest Park, Celbridge, Co. Kildare", 53.35401, -6.54603, LocalDateTime.of(2017, 01, 01, 19,46));
         static final Destination delivery71 = new Destination(71,"172 Woodview, Castletown, Celbridge, Co. Kildare", 53.34745, -6.53401, LocalDateTime.of(2017, 01, 01, 19,47));
         static final Destination delivery72 = new Destination(72,"116 Connaught Street, Kilcock, Co. Kildare", 53.39839, -6.66767, LocalDateTime.of(2017, 01, 01, 19,48));
         static final Destination delivery73 = new Destination(73,"35 The Paddocks, Oldtown Mill, Celbridge, Co. Kildare", 53.3473, -6.55057, LocalDateTime.of(2017, 01, 01, 19,48));
         static final Destination delivery74 = new Destination(74,"11 The Lodge,, Abbeylands,, Clane,, Co. Kildare", 53.29128, -6.67836, LocalDateTime.of(2017, 01, 01, 19,48));
         static final Destination delivery75 = new Destination(75,"113 Elton Court, Leixlip, Co. Kildare", 53.36158, -6.50533, LocalDateTime.of(2017, 01, 01, 19,48));
         static final Destination delivery76 = new Destination(76,"3 Greenfield Drive, Maynooth, Co. Kildare", 53.3727, -6.58757, LocalDateTime.of(2017, 01, 01, 19,49));
         static final Destination delivery77 = new Destination(77,"13 Castlevillage Lawns, Celbridge, Co. Kildare", 53.35321, -6.55412, LocalDateTime.of(2017, 01, 01, 19,50));
         static final Destination delivery78 = new Destination(78,"902 Lady Castle, K Club, Straffan, Co. Kildare", 53.31159, -6.60538, LocalDateTime.of(2017, 01, 01, 19,50));
         static final Destination delivery79 = new Destination(79,"13 Rinawade Close, Leixlip, Co. Kildare", 53.36455, -6.51435, LocalDateTime.of(2017, 01, 01, 19,50));
         static final Destination delivery80 = new Destination(80,"Apartment 1, The Lamps, School Street, Kilcock, Co. Kildare", 53.39999, -6.66807, LocalDateTime.of(2017, 01, 01, 19,50));
         static final Destination delivery81 = new Destination(81,"2 Beaufield Drive, Maynooth, Co. Kildare", 53.37414, -6.60028, LocalDateTime.of(2017, 01, 01, 19,51));
         static final Destination delivery82 = new Destination(82,"509 Riverforest, Leixlip, Co. Kildare", 53.37402, -6.49363, LocalDateTime.of(2017, 01, 01, 19,51));
         static final Destination delivery83 = new Destination(83,"43 The Green Moyglare Hall,Maynooth Co Kildare", 53.38983, -6.5951, LocalDateTime.of(2017, 01, 01, 19,51));
         static final Destination delivery84 = new Destination(84,"636 St.Patrick Park,Celbridge, Co.Kildare", 53.34033, -6.54596, LocalDateTime.of(2017, 01, 01, 19,52));
         static final Destination delivery85 = new Destination(85,"132 The Peninsula, Alexandra Walk, Clane, Co. Kildare", 53.28973, -6.67445, LocalDateTime.of(2017, 01, 01, 19,52));
         static final Destination delivery86 = new Destination(86,"14 Rye River Crescent, Dun Carraig, Leixlip, Co. Kildare", 53.36518, -6.48913, LocalDateTime.of(2017, 01, 01, 19,52));
         static final Destination delivery87 = new Destination(87,"348 Ryevale Lawns, Leixlip, Co. Kildare", 53.36873, -6.49619, LocalDateTime.of(2017, 01, 01, 19,52));
         static final Destination delivery88 = new Destination(88,"17 The Crescent, Abbey Farm,,Celbridge, Co.Kildare", 53.33256, -6.55056, LocalDateTime.of(2017, 01, 01, 19,52));
         static final Destination delivery89 = new Destination(89,"36 Castledawson,Maynooth Co Kildare", 53.37565, -6.60701, LocalDateTime.of(2017, 01, 01, 19,53));
         static final Destination delivery90 = new Destination(90,"28 The Lawn Moyglare Abbey,Maynooth Co Kildare", 53.38895, -6.60579, LocalDateTime.of(2017, 01, 01, 19,53));
         static final Destination delivery91 = new Destination(91,"104c Beatty Park, Celbridge, Co. Kildare", 53.34648, -6.54552, LocalDateTime.of(2017, 01, 01, 19,54));
         static final Destination delivery92 = new Destination(92,"40 Thornhill Meadows, Celbridge, Co. Kildare", 53.35202, -6.55099, LocalDateTime.of(2017, 01, 01, 19,55));
         static final Destination delivery93 = new Destination(93,"18 College Green, Maynooth, Co.Kildare", 53.37247, -6.60044, LocalDateTime.of(2017, 01, 01, 19,55));
         static final Destination delivery94 = new Destination(94,"1 Beaufield Crescent, Maynooth, Co Kildare", 53.37449, -6.60005, LocalDateTime.of(2017, 01, 01, 19,56));
         static final Destination delivery95 = new Destination(95,"6 Glen Easton Grove ,Leixlip Co Kildare", 53.36559, -6.51914, LocalDateTime.of(2017, 01, 01, 19,56));
         static final Destination delivery96 = new Destination(96,"14 The Avenue, Rochford, Bakers Walk, Kilcock, Co. Kildare", 53.39648, -6.66612, LocalDateTime.of(2017, 01, 01, 19,57));
         static final Destination delivery97 = new Destination(97,"7 Riverlawn, Abbeyfarm, Celbridge, Co. Kildare", 53.33239, -6.55163, LocalDateTime.of(2017, 01, 01, 19,57));
         static final Destination delivery98 = new Destination(98,"51 Royal Meadows, Kilcock, Co. Kildare", 53.39512, -6.67084, LocalDateTime.of(2017, 01, 01, 19,57));
         static final Destination delivery99 = new Destination(99,"96 Priory Lodge, St. Raphael's Manor, Celbridge, Co.Kildare", 53.33835, -6.53984, LocalDateTime.of(2017, 01, 01, 19,58));
         static final Destination delivery100 = new Destination(100,"18 Castle Dawson, Maynooth,, Co. Kildare", 53.37538, -6.60707, LocalDateTime.of(2017, 01, 01, 19,58));

      static ArrayList<Destination> queue;
      public static void main(String[] args){
        for(int x = 88; x< 98; x++){
          System.out.print(x+",");

          
          for(int yy = 1; yy< 60; yy++){
            if(yy%20==0)System.out.print(".");
            z = yy*0.01;
            for(qq = 1; qq< 60; qq++){
              if(qq%20==0)System.out.print("|");
              q = qq*0.1;
              for(t=-5;t<31;t++){
                TR(x,z);
              }
            }
          }
        }
        //TR(85, 2.81);


      }



      public static void TR(int x, double z){
                searchLateCount=0;
                drone = new Drone();
                queue = new ArrayList<Destination>();

                //Make Queue
                     queue.add(delivery1);
                     queue.add(delivery2);queue.add(delivery3);
                     queue.add(delivery4);queue.add(delivery5);
                     queue.add(delivery6);queue.add(delivery7);
                     queue.add(delivery8);queue.add(delivery9);
                     queue.add(delivery10);queue.add(delivery11);
                     queue.add(delivery12);queue.add(delivery13);
                     queue.add(delivery14);queue.add(delivery15);
                     queue.add(delivery16);queue.add(delivery17);
                     queue.add(delivery18);queue.add(delivery19);
                     queue.add(delivery20);queue.add(delivery21);
                     queue.add(delivery22);queue.add(delivery23);
                     queue.add(delivery24);queue.add(delivery25);
                     queue.add(delivery26);queue.add(delivery27);
                     queue.add(delivery28);queue.add(delivery29);
                     queue.add(delivery30);queue.add(delivery31);
                     queue.add(delivery32);queue.add(delivery33);
                     queue.add(delivery34);queue.add(delivery35);
                     queue.add(delivery36);queue.add(delivery37);
                     queue.add(delivery38);queue.add(delivery39);
                     queue.add(delivery40);queue.add(delivery41);
                     queue.add(delivery42);queue.add(delivery43);
                     queue.add(delivery44);queue.add(delivery45);
                     queue.add(delivery46);queue.add(delivery47);
                     queue.add(delivery48);queue.add(delivery49);
                     queue.add(delivery50);queue.add(delivery51);
                     queue.add(delivery52);queue.add(delivery53);
                     queue.add(delivery54);queue.add(delivery55);
                     queue.add(delivery56);queue.add(delivery57);
                     queue.add(delivery58);queue.add(delivery59);
                     queue.add(delivery60);queue.add(delivery61);
                     queue.add(delivery62);queue.add(delivery63);
                     queue.add(delivery64);queue.add(delivery65);
                     queue.add(delivery66);queue.add(delivery67);
                     queue.add(delivery68);queue.add(delivery69);
                     queue.add(delivery70);queue.add(delivery71);
                     queue.add(delivery72);queue.add(delivery73);
                     queue.add(delivery74);queue.add(delivery75);
                     queue.add(delivery76);queue.add(delivery77);
                     queue.add(delivery78);queue.add(delivery79);
                     queue.add(delivery80);queue.add(delivery81);
                     queue.add(delivery82);queue.add(delivery83);
                     queue.add(delivery84);queue.add(delivery85);
                     queue.add(delivery86);queue.add(delivery87);
                     queue.add(delivery88);queue.add(delivery89);
                     queue.add(delivery90);queue.add(delivery91);
                     queue.add(delivery92);queue.add(delivery93);
                     queue.add(delivery94);queue.add(delivery95);
                     queue.add(delivery96);queue.add(delivery97);
                     queue.add(delivery98);queue.add(delivery99);
                     queue.add(delivery100);

                 while(queue.size()>0){
                    Destination priority = queue.get(0);
                    SearchHorizon(priority, x, z);
                 }
                 //System.out.println("Journey: "+drone.journey);
                 //System.out.println("travelled: "+(int)(drone.mileage) +"m");
                 if(drone.lateMinutes< low){
                  low = drone.lateMinutes;
                  System.out.println();
                  System.out.println("lateMinutes "+drone.lateMinutes);
                  System.out.println("total time spent: "+ -1*drone.clock.until(LocalDateTime.of(2017,01,01,20,00),MINUTES) +" minutes");
                  System.out.println("x, z, q, t:  " +x+ " , " + z+" , "+q+" , "+t);
                  System.out.println(searchLateCount);
                  System.out.println("late Deliveries: "+drone.lateDeliveries.size());
                  System.out.println();
                  
                 }
                 //System.out.println("time now: " + drone.clock);

                 //
                 //System.out.println(drone.journey.size());
               //  System.out.println("Onway:Priority deliveries = "+drone.countOnways+":"+drone.countPriorities);
               
               
             

      }






      public static void SearchHorizon(Destination priority, int x, double z){
/* x IS THE MAX NUMBER OF NEARBY STOPS THE DRONE MAY CONSIDER VISITING BEFORE THE PRIORITY
y EFFECTS HOW FAR THE DRONE MAY DIVERT IT'S PATH FROM THE PRIORITY
x,y are the independant variables.
Their current values are chosen to minimise
 both 'lateMinutes' and 'lateDeliveries.size()'.*/
         int onWay = (queue.size()>=x) ? x:queue.size();
         double timeLeft = -drone.clock.until(priority.orderTime.plusMinutes(30), MINUTES);
          if(timeLeft>t){
          y = z*timeLeft;
         }else{
          searchLateCount++;
          y = q;
         }
          
         //DP - drone to Priority
         //DS - drone to possibleStop
         //SP - PossibleStop to Priority
         distanceDP = drone.getDistanceTo(priority);

         //CHECK FOR NEARBY STOPS BEFORE GOING TO PRIORITY
         for(int i = priority.index;i< onWay ;i++)
         {  if(queue.size()<i+1)break;
            Destination possibleStop = queue.get(i);
            if(
             (distanceDS = drone.getDistanceTo(possibleStop))
              +(distanceSP=possibleStop.getDistanceTo(priority))
              < y*distanceDP ){

                isThisPriority = false;
                queue.remove(i); i--;
                if(queue.size()>0) SearchHorizon(possibleStop, x, z);
                if(queue.size()<x) onWay--;
            }
         }
         //BETTER GO TO PRIORITY
            distanceDP = drone.getDistanceTo(priority);
            isThisPriority = true;
            queue.remove(priority);
            drone.makeDelivery(priority);
      }
}