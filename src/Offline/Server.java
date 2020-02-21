
package Offline;

/**
 *
 * @author Rifat
 */
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class Server extends javax.swing.JFrame implements Runnable{
    public static Server Srv=new Server();
    public BufferedReader Br;
    public OutputStreamWriter osw;
    public Socket sc;
    
    public static ArrayList<NameSocket> arra=new ArrayList<NameSocket>();
    public String str;
    public NameSocket NS;
    
    public final static int buffer_size=9022386;
    public static int total_size=0;
    
    public String receiver;
    public Socket receiverSocket;
    public String SendingFile;
    public int chunk_receive;
    public int summation;
    public int File_size;
    
    public int Total_Chunks;
    
   
    
    public InputStream IS;
    public OutputStream OS;
    
    public Server() {
        initComponents();
    }
    
    
    public Server(Socket s) throws IOException
    {
        IS=s.getInputStream();
        Br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        osw=new OutputStreamWriter(s.getOutputStream());
        sc=s;
    }
    
    
    public Socket getSocket(String s)
    {
        Socket sk=null;
        for(int i=0;i<arra.size();i++)
        {
            if(arra.get(i).str.equals(s)==true)
            {
                sk= arra.get(i).sckt;
                break;
            }
        }
        return sk;
    }
    
    
    
    @Override
    public void run() {
        //Checking existance, add, delete
        int byteRead;
        int curr=0;
        BufferedOutputStream BOS = null;
        FileOutputStream FOS = null;
        String str1=null;
        DefaultTableModel model=(DefaultTableModel)Srv.jTable2.getModel();
        Object row[]=new Object[1];
        BufferedInputStream BIS=null;
        String TimeOut;
         try
         {
             str=Br.readLine();
             NS=new NameSocket(str,sc);//Name Socket object creation
             if(arra.isEmpty()==true)
             {
                 arra.add(NS);
                 Srv.jTextArea1.append(str+" connected \n");
                 osw.write(str+" Added\n");
                 osw.flush();
                 row[0]=str;
                 model.addRow(row);
             }
             else
             {
                 if(getSocket(str)!=null)
                 {
                     Srv.jTextArea1.append(str+" Already Exist\n");
                     osw.write(str+" Already Exist\n");
                     osw.flush();
                     sc.close();
                     
                 }
                 else
                 {
                     osw.write(str+" Added\n");
                     osw.flush();
                     arra.add(NS);
                     Srv.jTextArea1.append(str+" connected \n");
                     row[0]=str;
                     model.addRow(row);
                 }
             }
             
             //Send receiver name
             while(true){
             str1=Br.readLine();
             if(str1.equals("Yes")==false && str1.equals("No")==false){
             while(true){
                
                Srv.jTextArea1.append(str+" asks for the receiver "+str1+"\n");
                Socket k=getSocket(str1);
                if(k==null)
                {
                    osw.write(str1+" is not available now...\n");
                    osw.flush();
                    str1=Br.readLine();
                }
                else
                {
                    if(str.compareToIgnoreCase(str1)==0)
                    {
                        osw.write("User's own ID. Cannot transfer file...\n");
                        osw.flush();
                        str1=Br.readLine();
                    }
                    else{
                        osw.write(str1+" is available, give file name please....\n");
                        osw.flush();
                        break;
                    }
                }
             }
             
             receiver=str1;
             //Send the file name
             System.out.println("Receiver : "+receiver);
             String Strm=Br.readLine();
             System.out.println(Strm);
             
             String str2=Strm.substring(0, Strm.indexOf("?"));//file name of the file that is dending by sender
             SendingFile=str2;
             System.out.println(SendingFile+" "+str2);
             int sz=Integer.parseInt(Strm.substring(Strm.indexOf("?")+1,Strm.length()));//file size of the file that is dending by sender
             File_size=sz;
             
             total_size=total_size+sz;
             System.out.println("Total Size : "+total_size);
             Srv.jTextArea1.append("Total size of the file :"+sz+" bytes\n");
             if(buffer_size>=total_size){
                Random rand=new Random();
                int kk=rand.nextInt(sz);//Chunk Size Random created by the server
                
                osw.write("Split and Transfer the file with Chunk_Size "+kk+"......"+" file_id "+SendingFile.substring(0, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length())+"\n");
                osw.flush();
                
                Total_Chunks=Integer.parseInt(Br.readLine());
                System.out.println("Total Chunks : "+Total_Chunks);
                
                
                //Serevr File Receive from sender
                for(int ii=1;ii<=Total_Chunks;ii++){
                    //int szz=Integer.parseInt(Br.readLine());
                    TimeOut=Br.readLine();
                    if(TimeOut.equals("Timeout"))
                    {
                        Srv.jTextArea1.append(str+" : Time out message accepted");
                        for(int kkm=1;kkm<ii;kkm++)
                        {
                            (new File("e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+Integer.toString(kkm)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length()))).delete();
                        }
                        break;
                    }
                    else{
                        chunk_receive=ii;
                        int szz=Integer.parseInt(TimeOut);
                        byte[] myby=new byte[szz];
                        FOS=new FileOutputStream(new File("e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+Integer.toString(ii)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length())));
                        int count=0;
                        while (count!=szz) {

                            byteRead = IS.read(myby);
                            System.out.println("byte Read "+byteRead);
                            System.out.println("total byte"+myby.length);
                            for(int i=0;i<byteRead;i++)
                            {
                                String s1 = String.format("%8s", Integer.toBinaryString(myby[i] & 0xFF)).replace(' ', '0');
                                System.out.println(s1);
                                System.out.println(myby[i]);
                            }
                            System.out.println(myby);
                            FOS.write(myby, 0, byteRead);
                            count+=byteRead;
                        }
                        FOS.close();
                        if(chunk_receive==Total_Chunks)
                        {
                            osw.write("Last Chunk has been Transferred to Server\n");
                            osw.flush();
                            String Complete=Br.readLine();
                            Srv.jTextArea1.append(Complete+"\n");
                            
                        }
                        else{

                            osw.write("Chunk"+ii+" has been transferred to server Successfully. Send next chunk. Time needs to transfer : \n");
                            osw.flush();
                        }
                    }
                }
                
                if(chunk_receive==Total_Chunks)
                {
                    int sum=0;
                    for(int dd=1;dd<=Total_Chunks;dd++)
                    {
                        sum=sum+(int)(new File("e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+Integer.toString(dd)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length()))).length();
                    }
                    summation=sum;
                    
                    if(summation==sz){
                
                    //Server send message to receiver client
                        receiverSocket=getSocket(str1);
                        OutputStreamWriter osw1=new OutputStreamWriter(getSocket(str1).getOutputStream());
                        //BufferedReader Br1=new BufferedReader(new InputStreamReader(getSocket(str1).getInputStream()));
                        osw1.write("Hello "+str1+", Are you ready to receive file "+"e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length())+" from "+str+" Total chunks "+Total_Chunks+" and total size "+summation+"?\n");
                        osw1.flush();
                     }
                    else
                    {
                        for(int dd=1;dd<=Total_Chunks;dd++)
                        {
                            (new File("e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+Integer.toString(dd)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length()))).delete();
                        }
                        total_size=total_size-sz;
                        Srv.jTextArea1.append("Chunk sizes' summation not equal Total size. All Chunks Deleted\n");
                    
                    }
                }
                
                
             }
             else {
                 total_size=total_size-sz;
                 osw.write("Buffer memory overflow...\n");
                 osw.flush();
             }
             }
             else{
                //String ssd=Br1.readLine();
                //System.out.println("Peraaaaaa");
                if(str1.equals("Yes"))
                {
                    
                    String Rece=Br.readLine();//FileId
                    System.out.println(Rece);
                    int ttl_chnk=Integer.parseInt(Br.readLine());//total chunks
                    Srv.jTextArea1.append("Receiver is ready to receive file. File transfering.... \n");
                //Server Send
                    for(int ddl=1;ddl<=ttl_chnk;ddl++){
                        osw.write((int)(new File(Rece.substring(0, Rece.lastIndexOf("."))+Integer.toString(ddl)+Rece.substring(Rece.lastIndexOf("."), Rece.length()))).length()+"\n");//chunk size
                        osw.flush();
                        String hh=Br.readLine();
                        byte[] myby1=new byte[(int)(new File(Rece.substring(0, Rece.lastIndexOf("."))+Integer.toString(ddl)+Rece.substring(Rece.lastIndexOf("."), Rece.length()))).length()];
                        BIS=new BufferedInputStream(new FileInputStream(new File(Rece.substring(0, Rece.lastIndexOf("."))+Integer.toString(ddl)+Rece.substring(Rece.lastIndexOf("."), Rece.length()))));
                        BIS.read(myby1,0,myby1.length);
                        OS=sc.getOutputStream();
                        OS.write(myby1,0,myby1.length);
                        System.out.println("pathay tohhhhhhhhhh");
                        OS.flush();
                        //OS.close();
                        BIS.close();
                        total_size=total_size-(int)(new File(Rece.substring(0, Rece.lastIndexOf("."))+Integer.toString(ddl)+Rece.substring(Rece.lastIndexOf("."), Rece.length()))).length();
                        new File(Rece.substring(0, Rece.lastIndexOf("."))+Integer.toString(ddl)+Rece.substring(Rece.lastIndexOf("."), Rece.length())).delete();
                        System.out.println("Total Size : "+total_size);
                        Srv.jTextArea1.append("Chunk deleted\n");
                        hh=Br.readLine();
                    }
                }
                else if(str1.equals("No"))
                {
                    Srv.jTextArea1.append(receiver+" refused to receive file...... \n");
                }
                
             }
             }
             
         }
         catch(IOException e)
         {
             
             if(chunk_receive<Total_Chunks){
             
                for(int dd=1;dd<=chunk_receive;dd++)
               {
                   (new File("e:/"+SendingFile.substring(3, SendingFile.lastIndexOf("."))+str.substring(4,7)+receiver.substring(4, 7)+Integer.toString(dd)+SendingFile.substring(SendingFile.lastIndexOf("."),SendingFile.length()))).delete();
                   total_size=total_size-File_size;
               }
                Srv.jTextArea1.append(str+" disconnected and All chunks deleted\n");
             }
             
             for(int j=0;j<model.getRowCount();j++)
             {
                 if(model.getValueAt(j, 0).equals(str)==true && getSocket(model.getValueAt(j, 0).toString())==sc)
                 {
                     Srv.jTextArea1.append(str+" disconnected\n");
                     model.removeRow(j);
                     break;
                 }
             }
             if(arra.contains(NS)==true){
                int j=arra.indexOf(NS);
                //System.out.println(j);
                arra.remove(j);
             }
         }
         
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVER");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Online"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        Srv.setVisible(true);
        try{
            ServerSocket SSoc=new ServerSocket(1193);
            while(true)
            {
                Socket So=SSoc.accept();
                Thread th=new Thread(new Server(So));
                th.start();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error "+e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    
}
