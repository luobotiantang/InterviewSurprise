# 算法相关

 - [判断一个链表是否有环](#判断一个链表是否有环)
 
 - [判断两个链表是否有交点](#判断两个链表是否有交点)
 
 - [链表反转](#链表反转)
 
 - [快速排序](#快速排序)
 
 - [冒泡排序](#冒泡排序)
 
 - [二分法查找](#二分法查找)
 
 - [二叉树先序、中序、后序遍历](#二叉树先序中序后序遍历)
 
 - [佩波那契数列](#佩波那契数列)
 
 - [判断字符串中包含完整的()子串的最大长度](#判断字符串中包含完整的()子串的最大长度)
 
 - [判断两个二叉树是否相等](#判断两个二叉树是否相等)
 
 - [字符串转float](#字符串转float)
 
 - [用sql查询出所有非叶子节点](#用sql查询出所有非叶子节点)
 
 - [LRU算法](#LRU算法)
 
 
 
 
 
 
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
 ### 二分法查找
     
     public static int binarySearch(int[] arr,int m,int l,int r){
        //先判断m是否在数组中及数组是否满足要求
        if(m<arr[l] || m>arr[r] || arr[l]<arr[r]){
            return -1;
        }
        //找中间值
        int mIndex = (l+r)/2;
        int mValue = arr[mIndex];
        if(mValue==m){
            return mValue;
        }else if(mValue>m){
            return binarySearch(arr,m,l,mIndex);
        }else{
            return binarySearch(arr,m,mIndex,arr.length);
        }
     }     
  
 ### 二叉树先序中序后序遍历
     
     //先定义一颗二叉树
     public class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val){
            this.val = val;
        }
     }
     
     先序遍历:(根节点-->左子树-->右子树)
     
        递归实现:
            public void preOrderTree(TreeNode node){
                if(node==null){
                    return;
                }
                System.out.println(node.val);
                preOrderTree(node.left);
                preOrderTree(node.right);
            }
        非递归实现:
            public ArrayList<Integer> preOrderTree(TreeNode node){
                ArrayList<Integer> list = new ArrayList<>();
                Stack<TreeNode> stack = new Stack<>();
                if(node==null){
                    return list;
                }
                stack.push(node);
                while(!stack.isEmpty()){
                    TreeNode temp = stack.pop();
                    if(temp!=null){
                        list.add(temp.val);
                        stack.push(node.right);
                        stack.push(node.left);
                    }
                }
            
            }
     
     中序遍历:(左子树-->根节点-->右子树)
        
        public static void inOrderTree(TreeNode node){
            if(node==null){
                return;
            }
            inOrderTree(node.left);
            System.out.println(node.val);
            inOrderTree(node.right);
        
        }
     
     后序遍历:(左子树-->右子树-->根节点)
        
        public static void postOrderTree(TreeNode node){
            if(node==null){
                return;
            }
            postOrderTree(node.left);
            System.out.println(node.val);
            postOrderTree(node.right);
        }
        
 ### 佩波那契数列
     
     1、递归实现
        public static long fibonacci(long l){
            if(l==0){
                return 0;
            }else if(l==1){
                return 1;
            }else{
                return fibonacci(l-1)+fibonacci(l-2);
            }
        }   
        
     2、非递归实现
        public static long fibonacci(long l){
            if(l==0){
                return 0;
            }else if(l==1){
                return 1;
            }else{
                long temp1 = 0;
                long temp2 = 1;
                long temp3 = 0;
                for(int i=2;i<=l;i++){
                    end = temp1+temp2;
                    temp1 = temp2;
                    temp2 = end;
                }
                return end;
            }
        }
 
 ### 判断字符串中包含完整的()子串的最大长度    
    
     String string = "()()()()()(())()((()))";
     //将字符串转化成数组
     char[] ch = string.toCharArray();
     StringBuffer strB = new StringBuffer();
     ...
     
 ### 判断两个二叉树是否相等
     
 ### 字符串转float     
     
 ### 用sql查询出所有非叶子节点
     
     CREATE TABLE `node` (
         `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
         `name` VARCHAR(32) NOT NULL,
         `pid` BIGINT NULL COMMENT '父节点ID'
     ); 
     请用sql查询出所有非叶子节点
     select id,name,pid from table where id not in (select distinct pid from table);
       
 ### LRU算法
     
     public class LRUCache<K,V> extends LinkedHashMap<K,V>{
        private final int CACHE_SIZE;
        
        public LRUCache(int cacheSize){
            //这块就是设置一个hashMap的初始值大小，同时最后一个true指的是让LinkedHashMap按照访问顺序进行排序，最近
            //访问的放在头，最老访问的就在尾
            super((int)Math.ceil(cacheSize/0.75)+1,0.75f,true);
            CACHE_SIZE=cacheSize;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > CACHE_SIZE; // 这个意思就是说当map中的数据量大于指定的缓存个数的时候，就自动删除最老的数据
        }
        
     }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     
     
     
     
> reubenwang@foxmail.com
> 没事别找我，找我也不在！--我很忙🦆