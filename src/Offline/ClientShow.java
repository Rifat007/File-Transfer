
package Offline;

/**
 *
 * @author Rifat
 */
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientShow extends javax.swing.JFrame {

    public Socket scc;
    public BufferedReader Br;
    public OutputStreamWriter osw;
    public String stri;
    public String strig;
    
    public int Chunk_Size;
    public int File_Size;
    public String File_Id;
    public int Total_Chunks;
    
    public String FN,txt;
    public int SendingChunkNum=0;
    
    public ClientShow() {
        initComponents();
    }
    public ClientShow(String ss,Socket s)
    {
        initComponents();
        scc=s;
        stri=ss;
        try{
        Br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        //os=s.getOutputStream();
        osw=new OutputStreamWriter(s.getOutputStream());
        String str=Br.readLine();
        jTextArea1.append(str+"\n");
        }catch(IOException e)
        {
            
        }
        //ListenThread(); 
    }
    
    public int Chunking(String FileName,String FileId,int File_size,int Chunk_size)
    {
        File inputFile = new File(FileName);
        FileInputStream inputStream;
        String newFileName;
        FileOutputStream filePart;
        int fileSize = File_size;
        int nChunks = 0, read = 0, readLength = Chunk_size;
        byte[] byteChunkPart;
        try {
            inputStream = new FileInputStream(inputFile);
            while (fileSize > 0) {
                if (fileSize <Chunk_size) {
                    readLength = fileSize;
                }
                byteChunkPart = new byte[readLength];
                read = inputStream.read(byteChunkPart, 0, readLength);
                fileSize -= read;
                assert (read == byteChunkPart.length);
                nChunks++;
                newFileName = FileId.substring(0, FileId.lastIndexOf("."))+Integer.toString(nChunks)+FileId.substring(FileId.lastIndexOf("."), FileId.length());
                filePart = new FileOutputStream(new File(newFileName));
                filePart.write(byteChunkPart);
                filePart.flush();
                filePart.close();
                byteChunkPart = null;
                filePart = null;
            }
            inputStream.close();
            //return nChunks;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return nChunks;
    }
    
    
    //Thread for Showing Message from Server 
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
//            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
                while ((stream = Br.readLine()) != null) 
                {
                    jTextArea1.append(stream+"\n");
                }

           }catch(Exception ex) { }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Log out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Receiver Name");

        jButton2.setText("Select Receiver");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("File Name");

        jButton3.setText("Send");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 102, 255));
        jButton4.setText("Transfer File");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Refresh");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Receive File");

        jButton6.setText("Split");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jButton3))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addGap(8, 8, 8)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //After logout 
        System.exit(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Receiver ID input
        try {
            osw.write(jTextField1.getText()+"\n");
            osw.flush();
            String as=Br.readLine();
            jTextArea1.append(as+"\n");
        } catch (IOException ex) {
            Logger.getLogger(ClientShow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //File name input and send
        try {
            FN=jTextField2.getText();
            int sz=(int)(new File(FN)).length();
            File_Size=sz;
            
            if(sz>0){
                osw.write(jTextField2.getText()+"?"+sz+"\n");
                osw.flush();
                System.out.println("Bhoot");
                txt=Br.readLine();
                jTextArea1.append(txt+"\n");
            }
            else
            {
                jTextArea1.append("File Not Exist. Re-enter the valid file\n");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClientShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

       
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Transfer File
        BufferedInputStream BIS=null;
        System.out.println(txt.substring(0,17));
        try{
            if(txt.substring(0,27).compareToIgnoreCase("Split and Transfer the file")==0)
            {   
                
                
                //File Sender to Server e jabe
                Date d1=new Date();
                Socket sk=scc;
                SendingChunkNum++;
                if(SendingChunkNum>Total_Chunks)
                {
                    jTextArea1.append("There is no Chunks anymore\n");
                }
                else{
                    osw.write((int)(new File(File_Id.substring(0, File_Id.lastIndexOf("."))+Integer.toString(SendingChunkNum)+File_Id.substring(File_Id.lastIndexOf("."),File_Id.length()))).length()+"\n");
                    osw.flush();
                    
                    System.out.println(SendingChunkNum);
                    System.out.println(File_Id.substring(0, File_Id.lastIndexOf("."))+Integer.toString(SendingChunkNum)+File_Id.substring(File_Id.lastIndexOf("."),File_Id.length()));
                    byte[] myby=new byte[(int)(new File(File_Id.substring(0, File_Id.lastIndexOf("."))+Integer.toString(SendingChunkNum)+File_Id.substring(File_Id.lastIndexOf("."),File_Id.length()))).length()];
                    BIS=new BufferedInputStream(new FileInputStream(new File(File_Id.substring(0, File_Id.lastIndexOf("."))+Integer.toString(SendingChunkNum)+File_Id.substring(File_Id.lastIndexOf("."),File_Id.length()))));
                    
                    BIS.read(myby,0,myby.length);
                    OutputStream os=sk.getOutputStream();
                    os.write(myby,0, myby.length);
                    os.flush();
                    
                    BIS.close();
                    
                    String msgg;
                    msgg=Br.readLine();
                    Date d2=new Date();
                    long diff=(d2.getTime()-d1.getTime());
                    jTextArea1.append(msgg+" in "+diff+" miliseconds.\n");
                    if(diff>30000)
                    {
                        txt="";
                    }
                    if(msgg.equals("Last Chunk has been Transferred to Server"))
                    {
                        osw.write("All Chunks transmission completed\n");
                        osw.flush();
                    }
                }
            }
            else
            {
                jTextArea1.append("File cannot be transferred\n");
                osw.write("Timeout\n");
                osw.flush();
            }
        }catch(IOException e)
        {
            
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       //Refresh
        try {
            strig=null;
           
                    
            if((strig=Br.readLine())!=null)
            {
                jTextArea1.append(strig+"\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientShow.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ListenThread();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        //yes or no
        try{
            int byteRead;
        if(jComboBox1.getSelectedItem().toString().equals("Yes")==true)
        {
            osw.write("Yes\n");
            osw.flush();
            
            osw.write(strig.substring(strig.indexOf("file")+5,strig.indexOf("from")-1)+"\n");//FileID
            osw.flush();
            String Receive_Fl_id=strig.substring(strig.indexOf("file")+5,strig.indexOf("from")-1);
            
            osw.write(strig.substring(strig.indexOf("chunks")+7,strig.indexOf("and")-1)+"\n");//Total Chunks
            osw.flush();
            int total_chnk=Integer.parseInt(strig.substring(strig.indexOf("chunks")+7,strig.indexOf("and")-1));
            System.out.println("total chunks : "+total_chnk);
            int cou=0;
            InputStream IS=scc.getInputStream();
            for(int ddl=1;ddl<=total_chnk;ddl++){
                
                System.out.println(" CHoleeeeeeee");
                FileOutputStream FOS=new FileOutputStream(new File("f:/"+Receive_Fl_id.substring(3, Receive_Fl_id.lastIndexOf("."))+Integer.toString(ddl)+Receive_Fl_id.substring(Receive_Fl_id.lastIndexOf("."), Receive_Fl_id.length())));
                
                System.out.println("Choleeeeeeeeeee");
                ////int count=0;
                byteRead=0;
                int sz=Integer.parseInt(Br.readLine());
                System.out.println("CHunk sizee "+sz);
                byte[] myby1=new byte[sz];
                osw.write("Done\n");
                osw.flush();
                
                //while(count!=sz)
                //{
                byteRead=IS.read(myby1);
                System.out.println(byteRead);
                FOS.write(myby1, 0, byteRead);
                    //count+=byteRead;
                //}
                osw.write("Done\n");
                osw.flush();
                FOS.close();
                cou++;
            }
            if(cou==total_chnk){
                jTextArea1.append("File has been transferred to Client\n");
            }
            
        }
        else
        {
            osw.write("No\n");
            osw.flush();
        }
        }catch(IOException e)
        {
            
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //Splitting
        try{
            if(txt.substring(0,27).compareToIgnoreCase("Split and Transfer the file")==0)
            {   
                Chunk_Size=Integer.parseInt(txt.substring(txt.indexOf("Size")+5, txt.indexOf("......")));
                File_Id=txt.substring(txt.indexOf("file_id")+8, txt.length());
                //Eikhane File Chunk hobe
                Total_Chunks=Chunking(FN,File_Id,File_Size,Chunk_Size);
                osw.write(Total_Chunks+"\n");
                osw.flush();
            }
            else
            {
                jTextArea1.append("Nothing to get Chunking\n");
            }
        }catch(IOException e)
        {
            
        }
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
