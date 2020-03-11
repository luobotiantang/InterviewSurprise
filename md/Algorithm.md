# 算法相关

 - [判断一个链表是否有环](#判断一个链表是否有环)
 
 - [判断两个链表是否有交点](#判断两个链表是否有交点)
 
 - [链表反转](#链表反转)
 
 - [快速排序](#快速排序)
 
 - [冒泡排序](#冒泡排序)
 
 
 
 
 ### 判断一个链表是否有环
 
     设置两个指针(fast,slow)初始值都指向头，slow每次前进一步，fast每次前进二步，如果链表存在环，则fast必定先进入环，而
     slow后进入环，两个指针必定相遇。（当然，fast先行头到尾部为NULL则为无环链表）
     
 ### 判断两个链表是否有交点
     
     1、直接法(采用暴力破解的方法，遍历两个链表，判断第一个链表的每个节点是否在第二个链表中)   
     2、Hash计数法(把第一个链表放进hash表，对第二个链表进行hash表的查询)
 
 ### 链表反转
 
     public class RevertList{
        public static Node revertListNode(Node head){
            //当前节点为null，当前节点的下一个节点为null,返回当前节点
            if(head =-null || head.getNext()==null){
                return head;
            }
            Node preNode = null;
            Node curNode = head;
            Node nextNode;
            while(curNode!=null){
                nextNode = curNode.getNext();
                curNode.setNext(preNode);
                preNode = curNode;
                curNode = nextNode;
            }
            return preNode;
        }
        class Node{
            public Object object;
            public Node next;
            public Node(Object object,Node next){
                this.object = object;
                this.next = next;
            }
            public Object getObject(){
                return object;
            }
            public void setObject(Object object){
                this.object = object;
            }
            public Node getNext(){
                return next;
            }
            public void setNext(Node next){
                this.next = next;
            }
        }
     }
     
 
 ### 快速排序
 
     快速排序
     快速排序思想：取一个数为基准数(通常取左边第一个数为基准数)
        1、从右往左扫描，如果基准数比它右边的数小则对右边的下标进行减1，如果基准数比它右边的数大则交换位置。
        2、从左往右扫描，如果基准数比它左边的数大则对左边的下标进行加1，如果基准数比它左边的数小则交换位置。
     //快速排序方法需要三个参数，第一个参数为数组，第二个参数和第三个参数分别为别数组的下标范围
     public static void quickSort(int[] arr,int l,int r){
        int i=l,j=r,temp;
        //对下表参数传值错误进行处理
        if(l>r){
            return;
        }
        //取数组从左边数第一个为基准数
        temp=arr[i];
        //如果两次扫描的下标不一致，一直进行数组的替换操作
        while(i!=j){
            //从数组的右边往左边扫描，如果基准数一直小于基准数右边的数那么对下标进行--操作
            while(i<j && temp<arr[j]){
                j--;
            }
            //如果基准数大于基准数右边的数，则进行数组替换位置
            arr[i]=arr[j];
            //从数组的左边往右扫描，如果基准数一直大于左边的数那么对下标进行++操作
            while(i<j && temp>arr[i]){
                i++;
            }
            //如果基准数小于左边的数
            arr[j]=arr[i];
        }
        arr[i]=temp;
        quickSort(arr,l,i-1);
        quickSort(arr,i+1,r);
     }
     
     //测试快速排序
     public static void main(String[] args){
         int[] arr = {1,2,5,6,8,0,7,30};
         quickSort(arr,0,arr.length-1);
         for(int i=0;i<arr.length;i++){
             System.out.println(arr[i]);
         }
     }     
     
 ### 冒泡排序    
     
         冒泡排序思想：从数组的下标第一个数开始和其右边的数进行比较，如果大于右边的数，则和右边的数进行替换。
         
         public static void bubbleSort(int[] arr){
            //声明一个临时变量
            int temp;
            for(int i=0;i<arr.length;i++){
                //必须要-1，为了防止数组下标越界
                for(int j=0;j<arr.length-i-1;j++){
                    if(arr[j]>arr[j+1]){
                        temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1]=temp;
                    }
                }
            }
         }
         
         public static void main(String[] args){
            int[] arr = {1,5,3,2,4,7,6,8};
            bubbleSort(arr);
            for(int i=0;i<arr.length;i++){
                System.out.println(arr[i]);
            }
         }
       
     
     
     
     
     
     
     
     
     
     
     
     
     
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆