package org.xtext.example.mydsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.xtext.example.mydsl.services.MyDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMyDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'PhysicalEpidemic'", "'{'", "'compartments'", "','", "'}'", "'flows'", "'PhysicalCompartment'", "'labels'", "'PhysicalFlow'", "'id'", "'from'", "'to'", "'equationtemplate'", "'Label'", "'EquationTemplate'", "'sourceParameters'", "'contactParameters'", "'contactCompartment'"
    };
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_ID=5;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalMyDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMyDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMyDslParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMyDsl.g"; }



     	private MyDslGrammarAccess grammarAccess;

        public InternalMyDslParser(TokenStream input, MyDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "PhysicalEpidemic";
       	}

       	@Override
       	protected MyDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRulePhysicalEpidemic"
    // InternalMyDsl.g:64:1: entryRulePhysicalEpidemic returns [EObject current=null] : iv_rulePhysicalEpidemic= rulePhysicalEpidemic EOF ;
    public final EObject entryRulePhysicalEpidemic() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePhysicalEpidemic = null;


        try {
            // InternalMyDsl.g:64:57: (iv_rulePhysicalEpidemic= rulePhysicalEpidemic EOF )
            // InternalMyDsl.g:65:2: iv_rulePhysicalEpidemic= rulePhysicalEpidemic EOF
            {
             newCompositeNode(grammarAccess.getPhysicalEpidemicRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePhysicalEpidemic=rulePhysicalEpidemic();

            state._fsp--;

             current =iv_rulePhysicalEpidemic; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePhysicalEpidemic"


    // $ANTLR start "rulePhysicalEpidemic"
    // InternalMyDsl.g:71:1: rulePhysicalEpidemic returns [EObject current=null] : ( () otherlv_1= 'PhysicalEpidemic' otherlv_2= '{' (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )? (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )? otherlv_15= '}' ) ;
    public final EObject rulePhysicalEpidemic() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        EObject lv_compartments_5_0 = null;

        EObject lv_compartments_7_0 = null;

        EObject lv_flows_11_0 = null;

        EObject lv_flows_13_0 = null;



        	enterRule();

        try {
            // InternalMyDsl.g:77:2: ( ( () otherlv_1= 'PhysicalEpidemic' otherlv_2= '{' (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )? (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )? otherlv_15= '}' ) )
            // InternalMyDsl.g:78:2: ( () otherlv_1= 'PhysicalEpidemic' otherlv_2= '{' (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )? (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )? otherlv_15= '}' )
            {
            // InternalMyDsl.g:78:2: ( () otherlv_1= 'PhysicalEpidemic' otherlv_2= '{' (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )? (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )? otherlv_15= '}' )
            // InternalMyDsl.g:79:3: () otherlv_1= 'PhysicalEpidemic' otherlv_2= '{' (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )? (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )? otherlv_15= '}'
            {
            // InternalMyDsl.g:79:3: ()
            // InternalMyDsl.g:80:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_4); 

            			newLeafNode(otherlv_2, grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMyDsl.g:94:3: (otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalMyDsl.g:95:4: otherlv_3= 'compartments' otherlv_4= '{' ( (lv_compartments_5_0= rulePhysicalCompartment ) ) (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )* otherlv_8= '}'
                    {
                    otherlv_3=(Token)match(input,13,FOLLOW_3); 

                    				newLeafNode(otherlv_3, grammarAccess.getPhysicalEpidemicAccess().getCompartmentsKeyword_3_0());
                    			
                    otherlv_4=(Token)match(input,12,FOLLOW_5); 

                    				newLeafNode(otherlv_4, grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_3_1());
                    			
                    // InternalMyDsl.g:103:4: ( (lv_compartments_5_0= rulePhysicalCompartment ) )
                    // InternalMyDsl.g:104:5: (lv_compartments_5_0= rulePhysicalCompartment )
                    {
                    // InternalMyDsl.g:104:5: (lv_compartments_5_0= rulePhysicalCompartment )
                    // InternalMyDsl.g:105:6: lv_compartments_5_0= rulePhysicalCompartment
                    {

                    						newCompositeNode(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_2_0());
                    					
                    pushFollow(FOLLOW_6);
                    lv_compartments_5_0=rulePhysicalCompartment();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPhysicalEpidemicRule());
                    						}
                    						add(
                    							current,
                    							"compartments",
                    							lv_compartments_5_0,
                    							"org.xtext.example.mydsl.MyDsl.PhysicalCompartment");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMyDsl.g:122:4: (otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) ) )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==14) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalMyDsl.g:123:5: otherlv_6= ',' ( (lv_compartments_7_0= rulePhysicalCompartment ) )
                    	    {
                    	    otherlv_6=(Token)match(input,14,FOLLOW_5); 

                    	    					newLeafNode(otherlv_6, grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_3_3_0());
                    	    				
                    	    // InternalMyDsl.g:127:5: ( (lv_compartments_7_0= rulePhysicalCompartment ) )
                    	    // InternalMyDsl.g:128:6: (lv_compartments_7_0= rulePhysicalCompartment )
                    	    {
                    	    // InternalMyDsl.g:128:6: (lv_compartments_7_0= rulePhysicalCompartment )
                    	    // InternalMyDsl.g:129:7: lv_compartments_7_0= rulePhysicalCompartment
                    	    {

                    	    							newCompositeNode(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_compartments_7_0=rulePhysicalCompartment();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getPhysicalEpidemicRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"compartments",
                    	    								lv_compartments_7_0,
                    	    								"org.xtext.example.mydsl.MyDsl.PhysicalCompartment");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_8, grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_3_4());
                    			

                    }
                    break;

            }

            // InternalMyDsl.g:152:3: (otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==16) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalMyDsl.g:153:4: otherlv_9= 'flows' otherlv_10= '{' ( (lv_flows_11_0= rulePhysicalFlow ) ) (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )* otherlv_14= '}'
                    {
                    otherlv_9=(Token)match(input,16,FOLLOW_3); 

                    				newLeafNode(otherlv_9, grammarAccess.getPhysicalEpidemicAccess().getFlowsKeyword_4_0());
                    			
                    otherlv_10=(Token)match(input,12,FOLLOW_8); 

                    				newLeafNode(otherlv_10, grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_4_1());
                    			
                    // InternalMyDsl.g:161:4: ( (lv_flows_11_0= rulePhysicalFlow ) )
                    // InternalMyDsl.g:162:5: (lv_flows_11_0= rulePhysicalFlow )
                    {
                    // InternalMyDsl.g:162:5: (lv_flows_11_0= rulePhysicalFlow )
                    // InternalMyDsl.g:163:6: lv_flows_11_0= rulePhysicalFlow
                    {

                    						newCompositeNode(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_2_0());
                    					
                    pushFollow(FOLLOW_6);
                    lv_flows_11_0=rulePhysicalFlow();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPhysicalEpidemicRule());
                    						}
                    						add(
                    							current,
                    							"flows",
                    							lv_flows_11_0,
                    							"org.xtext.example.mydsl.MyDsl.PhysicalFlow");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMyDsl.g:180:4: (otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==14) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalMyDsl.g:181:5: otherlv_12= ',' ( (lv_flows_13_0= rulePhysicalFlow ) )
                    	    {
                    	    otherlv_12=(Token)match(input,14,FOLLOW_8); 

                    	    					newLeafNode(otherlv_12, grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_4_3_0());
                    	    				
                    	    // InternalMyDsl.g:185:5: ( (lv_flows_13_0= rulePhysicalFlow ) )
                    	    // InternalMyDsl.g:186:6: (lv_flows_13_0= rulePhysicalFlow )
                    	    {
                    	    // InternalMyDsl.g:186:6: (lv_flows_13_0= rulePhysicalFlow )
                    	    // InternalMyDsl.g:187:7: lv_flows_13_0= rulePhysicalFlow
                    	    {

                    	    							newCompositeNode(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_flows_13_0=rulePhysicalFlow();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getPhysicalEpidemicRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"flows",
                    	    								lv_flows_13_0,
                    	    								"org.xtext.example.mydsl.MyDsl.PhysicalFlow");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,15,FOLLOW_9); 

                    				newLeafNode(otherlv_14, grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_4_4());
                    			

                    }
                    break;

            }

            otherlv_15=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_15, grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePhysicalEpidemic"


    // $ANTLR start "entryRulePhysicalCompartment"
    // InternalMyDsl.g:218:1: entryRulePhysicalCompartment returns [EObject current=null] : iv_rulePhysicalCompartment= rulePhysicalCompartment EOF ;
    public final EObject entryRulePhysicalCompartment() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePhysicalCompartment = null;


        try {
            // InternalMyDsl.g:218:60: (iv_rulePhysicalCompartment= rulePhysicalCompartment EOF )
            // InternalMyDsl.g:219:2: iv_rulePhysicalCompartment= rulePhysicalCompartment EOF
            {
             newCompositeNode(grammarAccess.getPhysicalCompartmentRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePhysicalCompartment=rulePhysicalCompartment();

            state._fsp--;

             current =iv_rulePhysicalCompartment; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePhysicalCompartment"


    // $ANTLR start "rulePhysicalCompartment"
    // InternalMyDsl.g:225:1: rulePhysicalCompartment returns [EObject current=null] : ( () otherlv_1= 'PhysicalCompartment' otherlv_2= '{' (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )? otherlv_9= '}' ) ;
    public final EObject rulePhysicalCompartment() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        EObject lv_labels_5_0 = null;

        EObject lv_labels_7_0 = null;



        	enterRule();

        try {
            // InternalMyDsl.g:231:2: ( ( () otherlv_1= 'PhysicalCompartment' otherlv_2= '{' (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )? otherlv_9= '}' ) )
            // InternalMyDsl.g:232:2: ( () otherlv_1= 'PhysicalCompartment' otherlv_2= '{' (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )? otherlv_9= '}' )
            {
            // InternalMyDsl.g:232:2: ( () otherlv_1= 'PhysicalCompartment' otherlv_2= '{' (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )? otherlv_9= '}' )
            // InternalMyDsl.g:233:3: () otherlv_1= 'PhysicalCompartment' otherlv_2= '{' (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )? otherlv_9= '}'
            {
            // InternalMyDsl.g:233:3: ()
            // InternalMyDsl.g:234:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,17,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_10); 

            			newLeafNode(otherlv_2, grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMyDsl.g:248:3: (otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalMyDsl.g:249:4: otherlv_3= 'labels' otherlv_4= '{' ( (lv_labels_5_0= ruleLabel ) ) (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )* otherlv_8= '}'
                    {
                    otherlv_3=(Token)match(input,18,FOLLOW_3); 

                    				newLeafNode(otherlv_3, grammarAccess.getPhysicalCompartmentAccess().getLabelsKeyword_3_0());
                    			
                    otherlv_4=(Token)match(input,12,FOLLOW_11); 

                    				newLeafNode(otherlv_4, grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_3_1());
                    			
                    // InternalMyDsl.g:257:4: ( (lv_labels_5_0= ruleLabel ) )
                    // InternalMyDsl.g:258:5: (lv_labels_5_0= ruleLabel )
                    {
                    // InternalMyDsl.g:258:5: (lv_labels_5_0= ruleLabel )
                    // InternalMyDsl.g:259:6: lv_labels_5_0= ruleLabel
                    {

                    						newCompositeNode(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_2_0());
                    					
                    pushFollow(FOLLOW_6);
                    lv_labels_5_0=ruleLabel();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPhysicalCompartmentRule());
                    						}
                    						add(
                    							current,
                    							"labels",
                    							lv_labels_5_0,
                    							"org.xtext.example.mydsl.MyDsl.Label");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMyDsl.g:276:4: (otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==14) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // InternalMyDsl.g:277:5: otherlv_6= ',' ( (lv_labels_7_0= ruleLabel ) )
                    	    {
                    	    otherlv_6=(Token)match(input,14,FOLLOW_11); 

                    	    					newLeafNode(otherlv_6, grammarAccess.getPhysicalCompartmentAccess().getCommaKeyword_3_3_0());
                    	    				
                    	    // InternalMyDsl.g:281:5: ( (lv_labels_7_0= ruleLabel ) )
                    	    // InternalMyDsl.g:282:6: (lv_labels_7_0= ruleLabel )
                    	    {
                    	    // InternalMyDsl.g:282:6: (lv_labels_7_0= ruleLabel )
                    	    // InternalMyDsl.g:283:7: lv_labels_7_0= ruleLabel
                    	    {

                    	    							newCompositeNode(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_6);
                    	    lv_labels_7_0=ruleLabel();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getPhysicalCompartmentRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"labels",
                    	    								lv_labels_7_0,
                    	    								"org.xtext.example.mydsl.MyDsl.Label");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,15,FOLLOW_9); 

                    				newLeafNode(otherlv_8, grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_3_4());
                    			

                    }
                    break;

            }

            otherlv_9=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePhysicalCompartment"


    // $ANTLR start "entryRulePhysicalFlow"
    // InternalMyDsl.g:314:1: entryRulePhysicalFlow returns [EObject current=null] : iv_rulePhysicalFlow= rulePhysicalFlow EOF ;
    public final EObject entryRulePhysicalFlow() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePhysicalFlow = null;


        try {
            // InternalMyDsl.g:314:53: (iv_rulePhysicalFlow= rulePhysicalFlow EOF )
            // InternalMyDsl.g:315:2: iv_rulePhysicalFlow= rulePhysicalFlow EOF
            {
             newCompositeNode(grammarAccess.getPhysicalFlowRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePhysicalFlow=rulePhysicalFlow();

            state._fsp--;

             current =iv_rulePhysicalFlow; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePhysicalFlow"


    // $ANTLR start "rulePhysicalFlow"
    // InternalMyDsl.g:321:1: rulePhysicalFlow returns [EObject current=null] : (otherlv_0= 'PhysicalFlow' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'from' ( ( ruleEString ) ) )? (otherlv_6= 'to' ( ( ruleEString ) ) )? otherlv_8= 'equationtemplate' ( (lv_equationtemplate_9_0= ruleEquationTemplate ) ) otherlv_10= '}' ) ;
    public final EObject rulePhysicalFlow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_id_3_0 = null;

        EObject lv_equationtemplate_9_0 = null;



        	enterRule();

        try {
            // InternalMyDsl.g:327:2: ( (otherlv_0= 'PhysicalFlow' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'from' ( ( ruleEString ) ) )? (otherlv_6= 'to' ( ( ruleEString ) ) )? otherlv_8= 'equationtemplate' ( (lv_equationtemplate_9_0= ruleEquationTemplate ) ) otherlv_10= '}' ) )
            // InternalMyDsl.g:328:2: (otherlv_0= 'PhysicalFlow' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'from' ( ( ruleEString ) ) )? (otherlv_6= 'to' ( ( ruleEString ) ) )? otherlv_8= 'equationtemplate' ( (lv_equationtemplate_9_0= ruleEquationTemplate ) ) otherlv_10= '}' )
            {
            // InternalMyDsl.g:328:2: (otherlv_0= 'PhysicalFlow' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'from' ( ( ruleEString ) ) )? (otherlv_6= 'to' ( ( ruleEString ) ) )? otherlv_8= 'equationtemplate' ( (lv_equationtemplate_9_0= ruleEquationTemplate ) ) otherlv_10= '}' )
            // InternalMyDsl.g:329:3: otherlv_0= 'PhysicalFlow' otherlv_1= '{' (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )? (otherlv_4= 'from' ( ( ruleEString ) ) )? (otherlv_6= 'to' ( ( ruleEString ) ) )? otherlv_8= 'equationtemplate' ( (lv_equationtemplate_9_0= ruleEquationTemplate ) ) otherlv_10= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getPhysicalFlowAccess().getPhysicalFlowKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getPhysicalFlowAccess().getLeftCurlyBracketKeyword_1());
            		
            // InternalMyDsl.g:337:3: (otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMyDsl.g:338:4: otherlv_2= 'id' ( (lv_id_3_0= ruleEString ) )
                    {
                    otherlv_2=(Token)match(input,20,FOLLOW_13); 

                    				newLeafNode(otherlv_2, grammarAccess.getPhysicalFlowAccess().getIdKeyword_2_0());
                    			
                    // InternalMyDsl.g:342:4: ( (lv_id_3_0= ruleEString ) )
                    // InternalMyDsl.g:343:5: (lv_id_3_0= ruleEString )
                    {
                    // InternalMyDsl.g:343:5: (lv_id_3_0= ruleEString )
                    // InternalMyDsl.g:344:6: lv_id_3_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getPhysicalFlowAccess().getIdEStringParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_14);
                    lv_id_3_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPhysicalFlowRule());
                    						}
                    						set(
                    							current,
                    							"id",
                    							lv_id_3_0,
                    							"org.xtext.example.mydsl.MyDsl.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMyDsl.g:362:3: (otherlv_4= 'from' ( ( ruleEString ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==21) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMyDsl.g:363:4: otherlv_4= 'from' ( ( ruleEString ) )
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_13); 

                    				newLeafNode(otherlv_4, grammarAccess.getPhysicalFlowAccess().getFromKeyword_3_0());
                    			
                    // InternalMyDsl.g:367:4: ( ( ruleEString ) )
                    // InternalMyDsl.g:368:5: ( ruleEString )
                    {
                    // InternalMyDsl.g:368:5: ( ruleEString )
                    // InternalMyDsl.g:369:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPhysicalFlowRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getPhysicalFlowAccess().getFromPhysicalCompartmentCrossReference_3_1_0());
                    					
                    pushFollow(FOLLOW_15);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMyDsl.g:384:3: (otherlv_6= 'to' ( ( ruleEString ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMyDsl.g:385:4: otherlv_6= 'to' ( ( ruleEString ) )
                    {
                    otherlv_6=(Token)match(input,22,FOLLOW_13); 

                    				newLeafNode(otherlv_6, grammarAccess.getPhysicalFlowAccess().getToKeyword_4_0());
                    			
                    // InternalMyDsl.g:389:4: ( ( ruleEString ) )
                    // InternalMyDsl.g:390:5: ( ruleEString )
                    {
                    // InternalMyDsl.g:390:5: ( ruleEString )
                    // InternalMyDsl.g:391:6: ruleEString
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPhysicalFlowRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getPhysicalFlowAccess().getToPhysicalCompartmentCrossReference_4_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    ruleEString();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,23,FOLLOW_17); 

            			newLeafNode(otherlv_8, grammarAccess.getPhysicalFlowAccess().getEquationtemplateKeyword_5());
            		
            // InternalMyDsl.g:410:3: ( (lv_equationtemplate_9_0= ruleEquationTemplate ) )
            // InternalMyDsl.g:411:4: (lv_equationtemplate_9_0= ruleEquationTemplate )
            {
            // InternalMyDsl.g:411:4: (lv_equationtemplate_9_0= ruleEquationTemplate )
            // InternalMyDsl.g:412:5: lv_equationtemplate_9_0= ruleEquationTemplate
            {

            					newCompositeNode(grammarAccess.getPhysicalFlowAccess().getEquationtemplateEquationTemplateParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_equationtemplate_9_0=ruleEquationTemplate();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPhysicalFlowRule());
            					}
            					set(
            						current,
            						"equationtemplate",
            						lv_equationtemplate_9_0,
            						"org.xtext.example.mydsl.MyDsl.EquationTemplate");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_10=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getPhysicalFlowAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePhysicalFlow"


    // $ANTLR start "entryRuleLabel"
    // InternalMyDsl.g:437:1: entryRuleLabel returns [EObject current=null] : iv_ruleLabel= ruleLabel EOF ;
    public final EObject entryRuleLabel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLabel = null;


        try {
            // InternalMyDsl.g:437:46: (iv_ruleLabel= ruleLabel EOF )
            // InternalMyDsl.g:438:2: iv_ruleLabel= ruleLabel EOF
            {
             newCompositeNode(grammarAccess.getLabelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLabel=ruleLabel();

            state._fsp--;

             current =iv_ruleLabel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLabel"


    // $ANTLR start "ruleLabel"
    // InternalMyDsl.g:444:1: ruleLabel returns [EObject current=null] : ( () otherlv_1= 'Label' ( (lv_name_2_0= ruleEString ) ) ) ;
    public final EObject ruleLabel() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;



        	enterRule();

        try {
            // InternalMyDsl.g:450:2: ( ( () otherlv_1= 'Label' ( (lv_name_2_0= ruleEString ) ) ) )
            // InternalMyDsl.g:451:2: ( () otherlv_1= 'Label' ( (lv_name_2_0= ruleEString ) ) )
            {
            // InternalMyDsl.g:451:2: ( () otherlv_1= 'Label' ( (lv_name_2_0= ruleEString ) ) )
            // InternalMyDsl.g:452:3: () otherlv_1= 'Label' ( (lv_name_2_0= ruleEString ) )
            {
            // InternalMyDsl.g:452:3: ()
            // InternalMyDsl.g:453:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getLabelAccess().getLabelAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,24,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getLabelAccess().getLabelKeyword_1());
            		
            // InternalMyDsl.g:463:3: ( (lv_name_2_0= ruleEString ) )
            // InternalMyDsl.g:464:4: (lv_name_2_0= ruleEString )
            {
            // InternalMyDsl.g:464:4: (lv_name_2_0= ruleEString )
            // InternalMyDsl.g:465:5: lv_name_2_0= ruleEString
            {

            					newCompositeNode(grammarAccess.getLabelAccess().getNameEStringParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_name_2_0=ruleEString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLabelRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.xtext.example.mydsl.MyDsl.EString");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLabel"


    // $ANTLR start "entryRuleEString"
    // InternalMyDsl.g:486:1: entryRuleEString returns [String current=null] : iv_ruleEString= ruleEString EOF ;
    public final String entryRuleEString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEString = null;


        try {
            // InternalMyDsl.g:486:47: (iv_ruleEString= ruleEString EOF )
            // InternalMyDsl.g:487:2: iv_ruleEString= ruleEString EOF
            {
             newCompositeNode(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEString=ruleEString();

            state._fsp--;

             current =iv_ruleEString.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalMyDsl.g:493:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) ;
    public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;


        	enterRule();

        try {
            // InternalMyDsl.g:499:2: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID ) )
            // InternalMyDsl.g:500:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            {
            // InternalMyDsl.g:500:2: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==RULE_STRING) ) {
                alt10=1;
            }
            else if ( (LA10_0==RULE_ID) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalMyDsl.g:501:3: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_0);
                    		

                    			newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMyDsl.g:509:3: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_1);
                    		

                    			newLeafNode(this_ID_1, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleEquationTemplate"
    // InternalMyDsl.g:520:1: entryRuleEquationTemplate returns [EObject current=null] : iv_ruleEquationTemplate= ruleEquationTemplate EOF ;
    public final EObject entryRuleEquationTemplate() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquationTemplate = null;


        try {
            // InternalMyDsl.g:520:57: (iv_ruleEquationTemplate= ruleEquationTemplate EOF )
            // InternalMyDsl.g:521:2: iv_ruleEquationTemplate= ruleEquationTemplate EOF
            {
             newCompositeNode(grammarAccess.getEquationTemplateRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEquationTemplate=ruleEquationTemplate();

            state._fsp--;

             current =iv_ruleEquationTemplate; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquationTemplate"


    // $ANTLR start "ruleEquationTemplate"
    // InternalMyDsl.g:527:1: ruleEquationTemplate returns [EObject current=null] : ( () otherlv_1= 'EquationTemplate' otherlv_2= '{' (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )? (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )? (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleEquationTemplate() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_sourceParameters_4_0 = null;

        AntlrDatatypeRuleToken lv_contactParameters_6_0 = null;

        AntlrDatatypeRuleToken lv_contactCompartment_8_0 = null;



        	enterRule();

        try {
            // InternalMyDsl.g:533:2: ( ( () otherlv_1= 'EquationTemplate' otherlv_2= '{' (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )? (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )? (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )? otherlv_9= '}' ) )
            // InternalMyDsl.g:534:2: ( () otherlv_1= 'EquationTemplate' otherlv_2= '{' (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )? (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )? (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )? otherlv_9= '}' )
            {
            // InternalMyDsl.g:534:2: ( () otherlv_1= 'EquationTemplate' otherlv_2= '{' (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )? (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )? (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )? otherlv_9= '}' )
            // InternalMyDsl.g:535:3: () otherlv_1= 'EquationTemplate' otherlv_2= '{' (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )? (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )? (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )? otherlv_9= '}'
            {
            // InternalMyDsl.g:535:3: ()
            // InternalMyDsl.g:536:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getEquationTemplateAccess().getEquationTemplateAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,25,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getEquationTemplateAccess().getEquationTemplateKeyword_1());
            		
            otherlv_2=(Token)match(input,12,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getEquationTemplateAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMyDsl.g:550:3: (otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==26) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMyDsl.g:551:4: otherlv_3= 'sourceParameters' ( (lv_sourceParameters_4_0= ruleEString ) )
                    {
                    otherlv_3=(Token)match(input,26,FOLLOW_13); 

                    				newLeafNode(otherlv_3, grammarAccess.getEquationTemplateAccess().getSourceParametersKeyword_3_0());
                    			
                    // InternalMyDsl.g:555:4: ( (lv_sourceParameters_4_0= ruleEString ) )
                    // InternalMyDsl.g:556:5: (lv_sourceParameters_4_0= ruleEString )
                    {
                    // InternalMyDsl.g:556:5: (lv_sourceParameters_4_0= ruleEString )
                    // InternalMyDsl.g:557:6: lv_sourceParameters_4_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getEquationTemplateAccess().getSourceParametersEStringParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_sourceParameters_4_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEquationTemplateRule());
                    						}
                    						set(
                    							current,
                    							"sourceParameters",
                    							lv_sourceParameters_4_0,
                    							"org.xtext.example.mydsl.MyDsl.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMyDsl.g:575:3: (otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==27) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMyDsl.g:576:4: otherlv_5= 'contactParameters' ( (lv_contactParameters_6_0= ruleEString ) )
                    {
                    otherlv_5=(Token)match(input,27,FOLLOW_13); 

                    				newLeafNode(otherlv_5, grammarAccess.getEquationTemplateAccess().getContactParametersKeyword_4_0());
                    			
                    // InternalMyDsl.g:580:4: ( (lv_contactParameters_6_0= ruleEString ) )
                    // InternalMyDsl.g:581:5: (lv_contactParameters_6_0= ruleEString )
                    {
                    // InternalMyDsl.g:581:5: (lv_contactParameters_6_0= ruleEString )
                    // InternalMyDsl.g:582:6: lv_contactParameters_6_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getEquationTemplateAccess().getContactParametersEStringParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_20);
                    lv_contactParameters_6_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEquationTemplateRule());
                    						}
                    						set(
                    							current,
                    							"contactParameters",
                    							lv_contactParameters_6_0,
                    							"org.xtext.example.mydsl.MyDsl.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMyDsl.g:600:3: (otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==28) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMyDsl.g:601:4: otherlv_7= 'contactCompartment' ( (lv_contactCompartment_8_0= ruleEString ) )
                    {
                    otherlv_7=(Token)match(input,28,FOLLOW_13); 

                    				newLeafNode(otherlv_7, grammarAccess.getEquationTemplateAccess().getContactCompartmentKeyword_5_0());
                    			
                    // InternalMyDsl.g:605:4: ( (lv_contactCompartment_8_0= ruleEString ) )
                    // InternalMyDsl.g:606:5: (lv_contactCompartment_8_0= ruleEString )
                    {
                    // InternalMyDsl.g:606:5: (lv_contactCompartment_8_0= ruleEString )
                    // InternalMyDsl.g:607:6: lv_contactCompartment_8_0= ruleEString
                    {

                    						newCompositeNode(grammarAccess.getEquationTemplateAccess().getContactCompartmentEStringParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_9);
                    lv_contactCompartment_8_0=ruleEString();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEquationTemplateRule());
                    						}
                    						set(
                    							current,
                    							"contactCompartment",
                    							lv_contactCompartment_8_0,
                    							"org.xtext.example.mydsl.MyDsl.EString");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getEquationTemplateAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquationTemplate"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x000000000001A000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000048000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000F00000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000E00000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000C00000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000000001C008000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000018008000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000010008000L});

}