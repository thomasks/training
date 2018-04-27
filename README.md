# training
yarn-statemachine
一、确定型有限自动机概述

	1. DFA形式化定义
		A={Q,∑,δ,q0,F}
			A是DFA的名称
			Q是状态集合
			∑是输入符号
			δ是转移函数
			q0是初始状态
			F是接收状态集合

	2. DFA的简化记号--转移图、转移表
		
二、YARN状态机

	1. YARN状态机概述
	
	2. YARN状态机的状态转换方式
	
	3. YARN状态机的构造过程
		a. 采用线性表尾插法构造状态转移函数列表
		b. 对状态转移函数进行入栈操作
		c. 状态转移函数出栈、构造状态表
		
	4. YARN状态机的转移过程
		a. 查找状态表,根据事件类型,查找状态转移函数
		b. 执行状态转移函数
		c. 变更状态机当前状态
	
	5. 一个简单的代码示例
		
三、YARN状态机的使用方式
	
	1. 同步方式
		状态转移和业务逻辑处于同一个线程中。

	2. 异步方式
		单独启动一个线程，处理状态转移。业务逻辑处理完成后，只是派发事件给状态处理线程。
	


