package com.firstapp.simple_calculator;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculator extends AppCompatActivity {

    int optop=-1,max,top=-1;
    Double[] eval=new Double[100];
    char opstack[]=new char[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculator_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calculator_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button_open_param = findViewById(R.id.button_open_param);
        Button button_close_param = findViewById(R.id.button_close_param);
        Button buttonc = findViewById(R.id.buttonC);
        Button buttonac = findViewById(R.id.buttonAC);
        Button button9 = findViewById(R.id.button9);
        Button button8 = findViewById(R.id.button8);
        Button button7 = findViewById(R.id.button7);
        Button button6 = findViewById(R.id.button6);
        Button button5 = findViewById(R.id.button5);
        Button button4 = findViewById(R.id.button4);
        Button button3 = findViewById(R.id.button3);
        Button button2 = findViewById(R.id.button2);
        Button button1 = findViewById(R.id.button1);
        Button button0 = findViewById(R.id.button0);
        Button buttondot = findViewById(R.id.button_dot);
        Button buttonadd = findViewById(R.id.button_add);
        Button buttonsub = findViewById(R.id.button_minus);
        Button buttonmul = findViewById(R.id.button_multiply);
        Button buttondiv = findViewById(R.id.button_div);
        Button buttonequal = findViewById(R.id.button_equals);

        TextView textview = findViewById(R.id.textspace);

        button_open_param.setOnClickListener(v -> {
            append_text("(");
        });
        button_close_param.setOnClickListener(v -> {
            append_text(")");
        });
        buttonc.setOnClickListener(v -> {
            clear_text();
        });
        buttonac.setOnClickListener(v -> {
            clear_all_text();
        });
        button9.setOnClickListener(v -> {
            append_text("9");
        });
        button8.setOnClickListener(v -> {
            append_text("8");
        });
        button7.setOnClickListener(v -> {
            append_text("7");
        });
        button6.setOnClickListener(v -> {
            append_text("6");
        });
        button5.setOnClickListener(v -> {
            append_text("5");
        });
        button4.setOnClickListener(v -> {
            append_text("4");
        });
        button3.setOnClickListener(v -> {
            append_text("3");
        });
        button2.setOnClickListener(v -> {
            append_text("2");
        });
        button1.setOnClickListener(v -> {
            append_text("1");
        });
        button0.setOnClickListener(v -> {
            append_text("0");
        });
        buttondot.setOnClickListener(v -> {
            append_text(".");
        });
        buttonadd.setOnClickListener(v -> {
            append_text("+");
        });
        buttonsub.setOnClickListener(v -> {
            append_text("-");
        });
        buttonmul.setOnClickListener(v -> {
            append_text("*");
        });
        buttondiv.setOnClickListener(v -> {
            append_text("/");
        });
        buttonequal.setOnClickListener(v -> {
            evaluate();
        });
    }

    private void append_text(String text) {
        Context context = getApplicationContext();
        TextView textview = findViewById(R.id.textspace);
        String tv = textview.getText().toString();
        Integer ln = tv.length();
        if (ln == 15) {
            Toast.makeText(context, "Sorry!, Reached maximum", Toast.LENGTH_SHORT).show();
        } else {
            textview.setText(tv + text);
        }
    }

    private void clear_text() {
        Context context = getApplicationContext();
        TextView textview = findViewById(R.id.textspace);
        String text = textview.getText().toString();
        if (text.length() == 0) {
            Toast.makeText(context, "Sorry!, Already empty", Toast.LENGTH_SHORT).show();
        } else {
            textview.setText(textview.getText().toString().substring(0, textview.getText().toString().length()-1));
        }
    }

    private void clear_all_text() {
        TextView textview = findViewById(R.id.textspace);
        textview.setText("");
    }

    private void evaluate() {
        TextView textview = findViewById(R.id.textspace);
        String exp = textview.getText().toString();
        String post = postfix(exp);
        String result = postevalu(post);
        textview.setText(result);
    }

    public String postfix(String exp){
        int i=0;
        String post="",space=" ";
        exp=exp+"}";
        char[] arrexp=exp.toCharArray();
        while(arrexp[i]!='}')
        {
            if(arrexp[i]=='(')
            {
                push('(');
            }
            else if(arrexp[i]==')')
            {
                while((optop!=-1)&&(opstack[optop]!='('))
                {
                    post=post+" "+Character.toString(pop());
                }
                char brace=pop();
            }
            else if(arrexp[i]=='+'||arrexp[i]=='-'||arrexp[i]=='*'||arrexp[i]=='/')
            {
                if(optop==-1||arrexp[i]!=')')
                {
                    push(arrexp[i]);
                    post=post+" ";
                }
                while((optop!=-1)&&(arrexp[i]!='(')&&(getprio(opstack[optop])>getprio(arrexp[i])))
                {
                    post=post+Character.toString(pop());
                }
            }
            else
            {
                post=post+Character.toString(arrexp[i]);
            }
            i=i+1;
        }
        post=post+space.toString();
        while((optop!=-1)&&(opstack[optop]!=')'))
        {
            post=post+Character.toString(pop());
        }
        post.trim();
        post=post+Character.toString('v');
        return post;
    }
    public void push(char val) {
        optop=optop+1;
        opstack[optop]=val;
    }
    public char pop() {
        char val=' ';
        if(optop>-1)
        {
            val=opstack[optop];
            optop=optop-1;
        }
        return val;
    }
    public int getprio(char op) {
        int pri=-1;
        if(op=='/'||op=='*')
            pri=1;
        else if(op=='+'||op=='-')
            pri=0;
        return pri;
    }

    public String postevalu(String post) {
        double num1=0,num2=0,value=0;
        int i=0,j=0;
        String temp="";
        char[] postexp=post.toCharArray();
        char chtemp;
        while(postexp[i]!='v')
        {
            chtemp=postexp[i];
            if(chtemp=='.'||chtemp=='1'||chtemp=='2'||chtemp=='3'||chtemp=='4'||chtemp=='5'||chtemp=='6'||chtemp=='7'||chtemp=='8'||chtemp=='9'||chtemp=='0')
            {
                temp=temp+Character.toString(postexp[i]);
            }
            else if(chtemp==' '&&temp!="")
            {
                pushev(Double.parseDouble(temp));
                temp="";
            }
            else if(chtemp=='+'||chtemp!='-'||chtemp!='*'||chtemp!='/')
            {
                num2=popev();
                num1=popev();
                switch (postexp[i]) {
                    case '+':
                        value=num1+num2;
                        break;
                    case '-':
                        value=num1-num2;
                        break;
                    case '*':
                        value=num1*num2;
                        break;
                    case '/':
                        value=num1/num2;
                        break;
                }
                pushev(value);
            }
            i=i+1;
        }
        return Double.toString(popev());
    }

    public void pushev(double val) {
        eval[++top]=val;
    }

    public double popev(){
        double val=0;
        if(top!=-1)
        {
            val=eval[top--];
        }
        return val;
    }
}