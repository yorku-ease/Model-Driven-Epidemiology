package org.xtext.example.mydsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.xtext.example.mydsl.services.MyDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMyDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'PhysicalEpidemic'", "'{'", "'}'", "'compartments'", "','", "'flows'", "'PhysicalCompartment'", "'labels'", "'PhysicalFlow'", "'equationtemplate'", "'id'", "'from'", "'to'", "'Label'", "'EquationTemplate'", "'sourceParameters'", "'contactParameters'", "'contactCompartment'"
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

    	public void setGrammarAccess(MyDslGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRulePhysicalEpidemic"
    // InternalMyDsl.g:53:1: entryRulePhysicalEpidemic : rulePhysicalEpidemic EOF ;
    public final void entryRulePhysicalEpidemic() throws RecognitionException {
        try {
            // InternalMyDsl.g:54:1: ( rulePhysicalEpidemic EOF )
            // InternalMyDsl.g:55:1: rulePhysicalEpidemic EOF
            {
             before(grammarAccess.getPhysicalEpidemicRule()); 
            pushFollow(FOLLOW_1);
            rulePhysicalEpidemic();

            state._fsp--;

             after(grammarAccess.getPhysicalEpidemicRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePhysicalEpidemic"


    // $ANTLR start "rulePhysicalEpidemic"
    // InternalMyDsl.g:62:1: rulePhysicalEpidemic : ( ( rule__PhysicalEpidemic__Group__0 ) ) ;
    public final void rulePhysicalEpidemic() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:66:2: ( ( ( rule__PhysicalEpidemic__Group__0 ) ) )
            // InternalMyDsl.g:67:2: ( ( rule__PhysicalEpidemic__Group__0 ) )
            {
            // InternalMyDsl.g:67:2: ( ( rule__PhysicalEpidemic__Group__0 ) )
            // InternalMyDsl.g:68:3: ( rule__PhysicalEpidemic__Group__0 )
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getGroup()); 
            // InternalMyDsl.g:69:3: ( rule__PhysicalEpidemic__Group__0 )
            // InternalMyDsl.g:69:4: rule__PhysicalEpidemic__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalEpidemicAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePhysicalEpidemic"


    // $ANTLR start "entryRulePhysicalCompartment"
    // InternalMyDsl.g:78:1: entryRulePhysicalCompartment : rulePhysicalCompartment EOF ;
    public final void entryRulePhysicalCompartment() throws RecognitionException {
        try {
            // InternalMyDsl.g:79:1: ( rulePhysicalCompartment EOF )
            // InternalMyDsl.g:80:1: rulePhysicalCompartment EOF
            {
             before(grammarAccess.getPhysicalCompartmentRule()); 
            pushFollow(FOLLOW_1);
            rulePhysicalCompartment();

            state._fsp--;

             after(grammarAccess.getPhysicalCompartmentRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePhysicalCompartment"


    // $ANTLR start "rulePhysicalCompartment"
    // InternalMyDsl.g:87:1: rulePhysicalCompartment : ( ( rule__PhysicalCompartment__Group__0 ) ) ;
    public final void rulePhysicalCompartment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:91:2: ( ( ( rule__PhysicalCompartment__Group__0 ) ) )
            // InternalMyDsl.g:92:2: ( ( rule__PhysicalCompartment__Group__0 ) )
            {
            // InternalMyDsl.g:92:2: ( ( rule__PhysicalCompartment__Group__0 ) )
            // InternalMyDsl.g:93:3: ( rule__PhysicalCompartment__Group__0 )
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getGroup()); 
            // InternalMyDsl.g:94:3: ( rule__PhysicalCompartment__Group__0 )
            // InternalMyDsl.g:94:4: rule__PhysicalCompartment__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalCompartmentAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePhysicalCompartment"


    // $ANTLR start "entryRulePhysicalFlow"
    // InternalMyDsl.g:103:1: entryRulePhysicalFlow : rulePhysicalFlow EOF ;
    public final void entryRulePhysicalFlow() throws RecognitionException {
        try {
            // InternalMyDsl.g:104:1: ( rulePhysicalFlow EOF )
            // InternalMyDsl.g:105:1: rulePhysicalFlow EOF
            {
             before(grammarAccess.getPhysicalFlowRule()); 
            pushFollow(FOLLOW_1);
            rulePhysicalFlow();

            state._fsp--;

             after(grammarAccess.getPhysicalFlowRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePhysicalFlow"


    // $ANTLR start "rulePhysicalFlow"
    // InternalMyDsl.g:112:1: rulePhysicalFlow : ( ( rule__PhysicalFlow__Group__0 ) ) ;
    public final void rulePhysicalFlow() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:116:2: ( ( ( rule__PhysicalFlow__Group__0 ) ) )
            // InternalMyDsl.g:117:2: ( ( rule__PhysicalFlow__Group__0 ) )
            {
            // InternalMyDsl.g:117:2: ( ( rule__PhysicalFlow__Group__0 ) )
            // InternalMyDsl.g:118:3: ( rule__PhysicalFlow__Group__0 )
            {
             before(grammarAccess.getPhysicalFlowAccess().getGroup()); 
            // InternalMyDsl.g:119:3: ( rule__PhysicalFlow__Group__0 )
            // InternalMyDsl.g:119:4: rule__PhysicalFlow__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalFlowAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePhysicalFlow"


    // $ANTLR start "entryRuleLabel"
    // InternalMyDsl.g:128:1: entryRuleLabel : ruleLabel EOF ;
    public final void entryRuleLabel() throws RecognitionException {
        try {
            // InternalMyDsl.g:129:1: ( ruleLabel EOF )
            // InternalMyDsl.g:130:1: ruleLabel EOF
            {
             before(grammarAccess.getLabelRule()); 
            pushFollow(FOLLOW_1);
            ruleLabel();

            state._fsp--;

             after(grammarAccess.getLabelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLabel"


    // $ANTLR start "ruleLabel"
    // InternalMyDsl.g:137:1: ruleLabel : ( ( rule__Label__Group__0 ) ) ;
    public final void ruleLabel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:141:2: ( ( ( rule__Label__Group__0 ) ) )
            // InternalMyDsl.g:142:2: ( ( rule__Label__Group__0 ) )
            {
            // InternalMyDsl.g:142:2: ( ( rule__Label__Group__0 ) )
            // InternalMyDsl.g:143:3: ( rule__Label__Group__0 )
            {
             before(grammarAccess.getLabelAccess().getGroup()); 
            // InternalMyDsl.g:144:3: ( rule__Label__Group__0 )
            // InternalMyDsl.g:144:4: rule__Label__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Label__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLabelAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLabel"


    // $ANTLR start "entryRuleEString"
    // InternalMyDsl.g:153:1: entryRuleEString : ruleEString EOF ;
    public final void entryRuleEString() throws RecognitionException {
        try {
            // InternalMyDsl.g:154:1: ( ruleEString EOF )
            // InternalMyDsl.g:155:1: ruleEString EOF
            {
             before(grammarAccess.getEStringRule()); 
            pushFollow(FOLLOW_1);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getEStringRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEString"


    // $ANTLR start "ruleEString"
    // InternalMyDsl.g:162:1: ruleEString : ( ( rule__EString__Alternatives ) ) ;
    public final void ruleEString() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:166:2: ( ( ( rule__EString__Alternatives ) ) )
            // InternalMyDsl.g:167:2: ( ( rule__EString__Alternatives ) )
            {
            // InternalMyDsl.g:167:2: ( ( rule__EString__Alternatives ) )
            // InternalMyDsl.g:168:3: ( rule__EString__Alternatives )
            {
             before(grammarAccess.getEStringAccess().getAlternatives()); 
            // InternalMyDsl.g:169:3: ( rule__EString__Alternatives )
            // InternalMyDsl.g:169:4: rule__EString__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__EString__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEStringAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEString"


    // $ANTLR start "entryRuleEquationTemplate"
    // InternalMyDsl.g:178:1: entryRuleEquationTemplate : ruleEquationTemplate EOF ;
    public final void entryRuleEquationTemplate() throws RecognitionException {
        try {
            // InternalMyDsl.g:179:1: ( ruleEquationTemplate EOF )
            // InternalMyDsl.g:180:1: ruleEquationTemplate EOF
            {
             before(grammarAccess.getEquationTemplateRule()); 
            pushFollow(FOLLOW_1);
            ruleEquationTemplate();

            state._fsp--;

             after(grammarAccess.getEquationTemplateRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEquationTemplate"


    // $ANTLR start "ruleEquationTemplate"
    // InternalMyDsl.g:187:1: ruleEquationTemplate : ( ( rule__EquationTemplate__Group__0 ) ) ;
    public final void ruleEquationTemplate() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:191:2: ( ( ( rule__EquationTemplate__Group__0 ) ) )
            // InternalMyDsl.g:192:2: ( ( rule__EquationTemplate__Group__0 ) )
            {
            // InternalMyDsl.g:192:2: ( ( rule__EquationTemplate__Group__0 ) )
            // InternalMyDsl.g:193:3: ( rule__EquationTemplate__Group__0 )
            {
             before(grammarAccess.getEquationTemplateAccess().getGroup()); 
            // InternalMyDsl.g:194:3: ( rule__EquationTemplate__Group__0 )
            // InternalMyDsl.g:194:4: rule__EquationTemplate__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEquationTemplateAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEquationTemplate"


    // $ANTLR start "rule__EString__Alternatives"
    // InternalMyDsl.g:202:1: rule__EString__Alternatives : ( ( RULE_STRING ) | ( RULE_ID ) );
    public final void rule__EString__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:206:1: ( ( RULE_STRING ) | ( RULE_ID ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_STRING) ) {
                alt1=1;
            }
            else if ( (LA1_0==RULE_ID) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalMyDsl.g:207:2: ( RULE_STRING )
                    {
                    // InternalMyDsl.g:207:2: ( RULE_STRING )
                    // InternalMyDsl.g:208:3: RULE_STRING
                    {
                     before(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 
                    match(input,RULE_STRING,FOLLOW_2); 
                     after(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMyDsl.g:213:2: ( RULE_ID )
                    {
                    // InternalMyDsl.g:213:2: ( RULE_ID )
                    // InternalMyDsl.g:214:3: RULE_ID
                    {
                     before(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EString__Alternatives"


    // $ANTLR start "rule__PhysicalEpidemic__Group__0"
    // InternalMyDsl.g:223:1: rule__PhysicalEpidemic__Group__0 : rule__PhysicalEpidemic__Group__0__Impl rule__PhysicalEpidemic__Group__1 ;
    public final void rule__PhysicalEpidemic__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:227:1: ( rule__PhysicalEpidemic__Group__0__Impl rule__PhysicalEpidemic__Group__1 )
            // InternalMyDsl.g:228:2: rule__PhysicalEpidemic__Group__0__Impl rule__PhysicalEpidemic__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__PhysicalEpidemic__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__0"


    // $ANTLR start "rule__PhysicalEpidemic__Group__0__Impl"
    // InternalMyDsl.g:235:1: rule__PhysicalEpidemic__Group__0__Impl : ( () ) ;
    public final void rule__PhysicalEpidemic__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:239:1: ( ( () ) )
            // InternalMyDsl.g:240:1: ( () )
            {
            // InternalMyDsl.g:240:1: ( () )
            // InternalMyDsl.g:241:2: ()
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicAction_0()); 
            // InternalMyDsl.g:242:2: ()
            // InternalMyDsl.g:242:3: 
            {
            }

             after(grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__0__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group__1"
    // InternalMyDsl.g:250:1: rule__PhysicalEpidemic__Group__1 : rule__PhysicalEpidemic__Group__1__Impl rule__PhysicalEpidemic__Group__2 ;
    public final void rule__PhysicalEpidemic__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:254:1: ( rule__PhysicalEpidemic__Group__1__Impl rule__PhysicalEpidemic__Group__2 )
            // InternalMyDsl.g:255:2: rule__PhysicalEpidemic__Group__1__Impl rule__PhysicalEpidemic__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalEpidemic__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__1"


    // $ANTLR start "rule__PhysicalEpidemic__Group__1__Impl"
    // InternalMyDsl.g:262:1: rule__PhysicalEpidemic__Group__1__Impl : ( 'PhysicalEpidemic' ) ;
    public final void rule__PhysicalEpidemic__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:266:1: ( ( 'PhysicalEpidemic' ) )
            // InternalMyDsl.g:267:1: ( 'PhysicalEpidemic' )
            {
            // InternalMyDsl.g:267:1: ( 'PhysicalEpidemic' )
            // InternalMyDsl.g:268:2: 'PhysicalEpidemic'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicKeyword_1()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getPhysicalEpidemicKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__1__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group__2"
    // InternalMyDsl.g:277:1: rule__PhysicalEpidemic__Group__2 : rule__PhysicalEpidemic__Group__2__Impl rule__PhysicalEpidemic__Group__3 ;
    public final void rule__PhysicalEpidemic__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:281:1: ( rule__PhysicalEpidemic__Group__2__Impl rule__PhysicalEpidemic__Group__3 )
            // InternalMyDsl.g:282:2: rule__PhysicalEpidemic__Group__2__Impl rule__PhysicalEpidemic__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__PhysicalEpidemic__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__2"


    // $ANTLR start "rule__PhysicalEpidemic__Group__2__Impl"
    // InternalMyDsl.g:289:1: rule__PhysicalEpidemic__Group__2__Impl : ( '{' ) ;
    public final void rule__PhysicalEpidemic__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:293:1: ( ( '{' ) )
            // InternalMyDsl.g:294:1: ( '{' )
            {
            // InternalMyDsl.g:294:1: ( '{' )
            // InternalMyDsl.g:295:2: '{'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__2__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group__3"
    // InternalMyDsl.g:304:1: rule__PhysicalEpidemic__Group__3 : rule__PhysicalEpidemic__Group__3__Impl rule__PhysicalEpidemic__Group__4 ;
    public final void rule__PhysicalEpidemic__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:308:1: ( rule__PhysicalEpidemic__Group__3__Impl rule__PhysicalEpidemic__Group__4 )
            // InternalMyDsl.g:309:2: rule__PhysicalEpidemic__Group__3__Impl rule__PhysicalEpidemic__Group__4
            {
            pushFollow(FOLLOW_5);
            rule__PhysicalEpidemic__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__3"


    // $ANTLR start "rule__PhysicalEpidemic__Group__3__Impl"
    // InternalMyDsl.g:316:1: rule__PhysicalEpidemic__Group__3__Impl : ( ( rule__PhysicalEpidemic__Group_3__0 )? ) ;
    public final void rule__PhysicalEpidemic__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:320:1: ( ( ( rule__PhysicalEpidemic__Group_3__0 )? ) )
            // InternalMyDsl.g:321:1: ( ( rule__PhysicalEpidemic__Group_3__0 )? )
            {
            // InternalMyDsl.g:321:1: ( ( rule__PhysicalEpidemic__Group_3__0 )? )
            // InternalMyDsl.g:322:2: ( rule__PhysicalEpidemic__Group_3__0 )?
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getGroup_3()); 
            // InternalMyDsl.g:323:2: ( rule__PhysicalEpidemic__Group_3__0 )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==14) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalMyDsl.g:323:3: rule__PhysicalEpidemic__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalEpidemic__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalEpidemicAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__3__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group__4"
    // InternalMyDsl.g:331:1: rule__PhysicalEpidemic__Group__4 : rule__PhysicalEpidemic__Group__4__Impl rule__PhysicalEpidemic__Group__5 ;
    public final void rule__PhysicalEpidemic__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:335:1: ( rule__PhysicalEpidemic__Group__4__Impl rule__PhysicalEpidemic__Group__5 )
            // InternalMyDsl.g:336:2: rule__PhysicalEpidemic__Group__4__Impl rule__PhysicalEpidemic__Group__5
            {
            pushFollow(FOLLOW_5);
            rule__PhysicalEpidemic__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__4"


    // $ANTLR start "rule__PhysicalEpidemic__Group__4__Impl"
    // InternalMyDsl.g:343:1: rule__PhysicalEpidemic__Group__4__Impl : ( ( rule__PhysicalEpidemic__Group_4__0 )? ) ;
    public final void rule__PhysicalEpidemic__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:347:1: ( ( ( rule__PhysicalEpidemic__Group_4__0 )? ) )
            // InternalMyDsl.g:348:1: ( ( rule__PhysicalEpidemic__Group_4__0 )? )
            {
            // InternalMyDsl.g:348:1: ( ( rule__PhysicalEpidemic__Group_4__0 )? )
            // InternalMyDsl.g:349:2: ( rule__PhysicalEpidemic__Group_4__0 )?
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getGroup_4()); 
            // InternalMyDsl.g:350:2: ( rule__PhysicalEpidemic__Group_4__0 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==16) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalMyDsl.g:350:3: rule__PhysicalEpidemic__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalEpidemic__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalEpidemicAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__4__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group__5"
    // InternalMyDsl.g:358:1: rule__PhysicalEpidemic__Group__5 : rule__PhysicalEpidemic__Group__5__Impl ;
    public final void rule__PhysicalEpidemic__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:362:1: ( rule__PhysicalEpidemic__Group__5__Impl )
            // InternalMyDsl.g:363:2: rule__PhysicalEpidemic__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__5"


    // $ANTLR start "rule__PhysicalEpidemic__Group__5__Impl"
    // InternalMyDsl.g:369:1: rule__PhysicalEpidemic__Group__5__Impl : ( '}' ) ;
    public final void rule__PhysicalEpidemic__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:373:1: ( ( '}' ) )
            // InternalMyDsl.g:374:1: ( '}' )
            {
            // InternalMyDsl.g:374:1: ( '}' )
            // InternalMyDsl.g:375:2: '}'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_5()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group__5__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__0"
    // InternalMyDsl.g:385:1: rule__PhysicalEpidemic__Group_3__0 : rule__PhysicalEpidemic__Group_3__0__Impl rule__PhysicalEpidemic__Group_3__1 ;
    public final void rule__PhysicalEpidemic__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:389:1: ( rule__PhysicalEpidemic__Group_3__0__Impl rule__PhysicalEpidemic__Group_3__1 )
            // InternalMyDsl.g:390:2: rule__PhysicalEpidemic__Group_3__0__Impl rule__PhysicalEpidemic__Group_3__1
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalEpidemic__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__0"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__0__Impl"
    // InternalMyDsl.g:397:1: rule__PhysicalEpidemic__Group_3__0__Impl : ( 'compartments' ) ;
    public final void rule__PhysicalEpidemic__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:401:1: ( ( 'compartments' ) )
            // InternalMyDsl.g:402:1: ( 'compartments' )
            {
            // InternalMyDsl.g:402:1: ( 'compartments' )
            // InternalMyDsl.g:403:2: 'compartments'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsKeyword_3_0()); 
            match(input,14,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__0__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__1"
    // InternalMyDsl.g:412:1: rule__PhysicalEpidemic__Group_3__1 : rule__PhysicalEpidemic__Group_3__1__Impl rule__PhysicalEpidemic__Group_3__2 ;
    public final void rule__PhysicalEpidemic__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:416:1: ( rule__PhysicalEpidemic__Group_3__1__Impl rule__PhysicalEpidemic__Group_3__2 )
            // InternalMyDsl.g:417:2: rule__PhysicalEpidemic__Group_3__1__Impl rule__PhysicalEpidemic__Group_3__2
            {
            pushFollow(FOLLOW_6);
            rule__PhysicalEpidemic__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__1"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__1__Impl"
    // InternalMyDsl.g:424:1: rule__PhysicalEpidemic__Group_3__1__Impl : ( '{' ) ;
    public final void rule__PhysicalEpidemic__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:428:1: ( ( '{' ) )
            // InternalMyDsl.g:429:1: ( '{' )
            {
            // InternalMyDsl.g:429:1: ( '{' )
            // InternalMyDsl.g:430:2: '{'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_3_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__1__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__2"
    // InternalMyDsl.g:439:1: rule__PhysicalEpidemic__Group_3__2 : rule__PhysicalEpidemic__Group_3__2__Impl rule__PhysicalEpidemic__Group_3__3 ;
    public final void rule__PhysicalEpidemic__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:443:1: ( rule__PhysicalEpidemic__Group_3__2__Impl rule__PhysicalEpidemic__Group_3__3 )
            // InternalMyDsl.g:444:2: rule__PhysicalEpidemic__Group_3__2__Impl rule__PhysicalEpidemic__Group_3__3
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalEpidemic__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__2"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__2__Impl"
    // InternalMyDsl.g:451:1: rule__PhysicalEpidemic__Group_3__2__Impl : ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 ) ) ;
    public final void rule__PhysicalEpidemic__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:455:1: ( ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 ) ) )
            // InternalMyDsl.g:456:1: ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 ) )
            {
            // InternalMyDsl.g:456:1: ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 ) )
            // InternalMyDsl.g:457:2: ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 )
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsAssignment_3_2()); 
            // InternalMyDsl.g:458:2: ( rule__PhysicalEpidemic__CompartmentsAssignment_3_2 )
            // InternalMyDsl.g:458:3: rule__PhysicalEpidemic__CompartmentsAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__CompartmentsAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsAssignment_3_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__2__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__3"
    // InternalMyDsl.g:466:1: rule__PhysicalEpidemic__Group_3__3 : rule__PhysicalEpidemic__Group_3__3__Impl rule__PhysicalEpidemic__Group_3__4 ;
    public final void rule__PhysicalEpidemic__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:470:1: ( rule__PhysicalEpidemic__Group_3__3__Impl rule__PhysicalEpidemic__Group_3__4 )
            // InternalMyDsl.g:471:2: rule__PhysicalEpidemic__Group_3__3__Impl rule__PhysicalEpidemic__Group_3__4
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalEpidemic__Group_3__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__3"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__3__Impl"
    // InternalMyDsl.g:478:1: rule__PhysicalEpidemic__Group_3__3__Impl : ( ( rule__PhysicalEpidemic__Group_3_3__0 )* ) ;
    public final void rule__PhysicalEpidemic__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:482:1: ( ( ( rule__PhysicalEpidemic__Group_3_3__0 )* ) )
            // InternalMyDsl.g:483:1: ( ( rule__PhysicalEpidemic__Group_3_3__0 )* )
            {
            // InternalMyDsl.g:483:1: ( ( rule__PhysicalEpidemic__Group_3_3__0 )* )
            // InternalMyDsl.g:484:2: ( rule__PhysicalEpidemic__Group_3_3__0 )*
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getGroup_3_3()); 
            // InternalMyDsl.g:485:2: ( rule__PhysicalEpidemic__Group_3_3__0 )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==15) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalMyDsl.g:485:3: rule__PhysicalEpidemic__Group_3_3__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__PhysicalEpidemic__Group_3_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

             after(grammarAccess.getPhysicalEpidemicAccess().getGroup_3_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__3__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__4"
    // InternalMyDsl.g:493:1: rule__PhysicalEpidemic__Group_3__4 : rule__PhysicalEpidemic__Group_3__4__Impl ;
    public final void rule__PhysicalEpidemic__Group_3__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:497:1: ( rule__PhysicalEpidemic__Group_3__4__Impl )
            // InternalMyDsl.g:498:2: rule__PhysicalEpidemic__Group_3__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__4"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3__4__Impl"
    // InternalMyDsl.g:504:1: rule__PhysicalEpidemic__Group_3__4__Impl : ( '}' ) ;
    public final void rule__PhysicalEpidemic__Group_3__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:508:1: ( ( '}' ) )
            // InternalMyDsl.g:509:1: ( '}' )
            {
            // InternalMyDsl.g:509:1: ( '}' )
            // InternalMyDsl.g:510:2: '}'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_3_4()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_3_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3__4__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3_3__0"
    // InternalMyDsl.g:520:1: rule__PhysicalEpidemic__Group_3_3__0 : rule__PhysicalEpidemic__Group_3_3__0__Impl rule__PhysicalEpidemic__Group_3_3__1 ;
    public final void rule__PhysicalEpidemic__Group_3_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:524:1: ( rule__PhysicalEpidemic__Group_3_3__0__Impl rule__PhysicalEpidemic__Group_3_3__1 )
            // InternalMyDsl.g:525:2: rule__PhysicalEpidemic__Group_3_3__0__Impl rule__PhysicalEpidemic__Group_3_3__1
            {
            pushFollow(FOLLOW_6);
            rule__PhysicalEpidemic__Group_3_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3_3__0"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3_3__0__Impl"
    // InternalMyDsl.g:532:1: rule__PhysicalEpidemic__Group_3_3__0__Impl : ( ',' ) ;
    public final void rule__PhysicalEpidemic__Group_3_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:536:1: ( ( ',' ) )
            // InternalMyDsl.g:537:1: ( ',' )
            {
            // InternalMyDsl.g:537:1: ( ',' )
            // InternalMyDsl.g:538:2: ','
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_3_3_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_3_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3_3__0__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3_3__1"
    // InternalMyDsl.g:547:1: rule__PhysicalEpidemic__Group_3_3__1 : rule__PhysicalEpidemic__Group_3_3__1__Impl ;
    public final void rule__PhysicalEpidemic__Group_3_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:551:1: ( rule__PhysicalEpidemic__Group_3_3__1__Impl )
            // InternalMyDsl.g:552:2: rule__PhysicalEpidemic__Group_3_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_3_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3_3__1"


    // $ANTLR start "rule__PhysicalEpidemic__Group_3_3__1__Impl"
    // InternalMyDsl.g:558:1: rule__PhysicalEpidemic__Group_3_3__1__Impl : ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 ) ) ;
    public final void rule__PhysicalEpidemic__Group_3_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:562:1: ( ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 ) ) )
            // InternalMyDsl.g:563:1: ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 ) )
            {
            // InternalMyDsl.g:563:1: ( ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 ) )
            // InternalMyDsl.g:564:2: ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 )
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsAssignment_3_3_1()); 
            // InternalMyDsl.g:565:2: ( rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 )
            // InternalMyDsl.g:565:3: rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsAssignment_3_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_3_3__1__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__0"
    // InternalMyDsl.g:574:1: rule__PhysicalEpidemic__Group_4__0 : rule__PhysicalEpidemic__Group_4__0__Impl rule__PhysicalEpidemic__Group_4__1 ;
    public final void rule__PhysicalEpidemic__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:578:1: ( rule__PhysicalEpidemic__Group_4__0__Impl rule__PhysicalEpidemic__Group_4__1 )
            // InternalMyDsl.g:579:2: rule__PhysicalEpidemic__Group_4__0__Impl rule__PhysicalEpidemic__Group_4__1
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalEpidemic__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__0"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__0__Impl"
    // InternalMyDsl.g:586:1: rule__PhysicalEpidemic__Group_4__0__Impl : ( 'flows' ) ;
    public final void rule__PhysicalEpidemic__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:590:1: ( ( 'flows' ) )
            // InternalMyDsl.g:591:1: ( 'flows' )
            {
            // InternalMyDsl.g:591:1: ( 'flows' )
            // InternalMyDsl.g:592:2: 'flows'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getFlowsKeyword_4_0()); 
            match(input,16,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getFlowsKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__0__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__1"
    // InternalMyDsl.g:601:1: rule__PhysicalEpidemic__Group_4__1 : rule__PhysicalEpidemic__Group_4__1__Impl rule__PhysicalEpidemic__Group_4__2 ;
    public final void rule__PhysicalEpidemic__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:605:1: ( rule__PhysicalEpidemic__Group_4__1__Impl rule__PhysicalEpidemic__Group_4__2 )
            // InternalMyDsl.g:606:2: rule__PhysicalEpidemic__Group_4__1__Impl rule__PhysicalEpidemic__Group_4__2
            {
            pushFollow(FOLLOW_9);
            rule__PhysicalEpidemic__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__1"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__1__Impl"
    // InternalMyDsl.g:613:1: rule__PhysicalEpidemic__Group_4__1__Impl : ( '{' ) ;
    public final void rule__PhysicalEpidemic__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:617:1: ( ( '{' ) )
            // InternalMyDsl.g:618:1: ( '{' )
            {
            // InternalMyDsl.g:618:1: ( '{' )
            // InternalMyDsl.g:619:2: '{'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_4_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getLeftCurlyBracketKeyword_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__1__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__2"
    // InternalMyDsl.g:628:1: rule__PhysicalEpidemic__Group_4__2 : rule__PhysicalEpidemic__Group_4__2__Impl rule__PhysicalEpidemic__Group_4__3 ;
    public final void rule__PhysicalEpidemic__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:632:1: ( rule__PhysicalEpidemic__Group_4__2__Impl rule__PhysicalEpidemic__Group_4__3 )
            // InternalMyDsl.g:633:2: rule__PhysicalEpidemic__Group_4__2__Impl rule__PhysicalEpidemic__Group_4__3
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalEpidemic__Group_4__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__2"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__2__Impl"
    // InternalMyDsl.g:640:1: rule__PhysicalEpidemic__Group_4__2__Impl : ( ( rule__PhysicalEpidemic__FlowsAssignment_4_2 ) ) ;
    public final void rule__PhysicalEpidemic__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:644:1: ( ( ( rule__PhysicalEpidemic__FlowsAssignment_4_2 ) ) )
            // InternalMyDsl.g:645:1: ( ( rule__PhysicalEpidemic__FlowsAssignment_4_2 ) )
            {
            // InternalMyDsl.g:645:1: ( ( rule__PhysicalEpidemic__FlowsAssignment_4_2 ) )
            // InternalMyDsl.g:646:2: ( rule__PhysicalEpidemic__FlowsAssignment_4_2 )
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getFlowsAssignment_4_2()); 
            // InternalMyDsl.g:647:2: ( rule__PhysicalEpidemic__FlowsAssignment_4_2 )
            // InternalMyDsl.g:647:3: rule__PhysicalEpidemic__FlowsAssignment_4_2
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__FlowsAssignment_4_2();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalEpidemicAccess().getFlowsAssignment_4_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__2__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__3"
    // InternalMyDsl.g:655:1: rule__PhysicalEpidemic__Group_4__3 : rule__PhysicalEpidemic__Group_4__3__Impl rule__PhysicalEpidemic__Group_4__4 ;
    public final void rule__PhysicalEpidemic__Group_4__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:659:1: ( rule__PhysicalEpidemic__Group_4__3__Impl rule__PhysicalEpidemic__Group_4__4 )
            // InternalMyDsl.g:660:2: rule__PhysicalEpidemic__Group_4__3__Impl rule__PhysicalEpidemic__Group_4__4
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalEpidemic__Group_4__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__3"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__3__Impl"
    // InternalMyDsl.g:667:1: rule__PhysicalEpidemic__Group_4__3__Impl : ( ( rule__PhysicalEpidemic__Group_4_3__0 )* ) ;
    public final void rule__PhysicalEpidemic__Group_4__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:671:1: ( ( ( rule__PhysicalEpidemic__Group_4_3__0 )* ) )
            // InternalMyDsl.g:672:1: ( ( rule__PhysicalEpidemic__Group_4_3__0 )* )
            {
            // InternalMyDsl.g:672:1: ( ( rule__PhysicalEpidemic__Group_4_3__0 )* )
            // InternalMyDsl.g:673:2: ( rule__PhysicalEpidemic__Group_4_3__0 )*
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getGroup_4_3()); 
            // InternalMyDsl.g:674:2: ( rule__PhysicalEpidemic__Group_4_3__0 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==15) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalMyDsl.g:674:3: rule__PhysicalEpidemic__Group_4_3__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__PhysicalEpidemic__Group_4_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             after(grammarAccess.getPhysicalEpidemicAccess().getGroup_4_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__3__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__4"
    // InternalMyDsl.g:682:1: rule__PhysicalEpidemic__Group_4__4 : rule__PhysicalEpidemic__Group_4__4__Impl ;
    public final void rule__PhysicalEpidemic__Group_4__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:686:1: ( rule__PhysicalEpidemic__Group_4__4__Impl )
            // InternalMyDsl.g:687:2: rule__PhysicalEpidemic__Group_4__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__4"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4__4__Impl"
    // InternalMyDsl.g:693:1: rule__PhysicalEpidemic__Group_4__4__Impl : ( '}' ) ;
    public final void rule__PhysicalEpidemic__Group_4__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:697:1: ( ( '}' ) )
            // InternalMyDsl.g:698:1: ( '}' )
            {
            // InternalMyDsl.g:698:1: ( '}' )
            // InternalMyDsl.g:699:2: '}'
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_4_4()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getRightCurlyBracketKeyword_4_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4__4__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4_3__0"
    // InternalMyDsl.g:709:1: rule__PhysicalEpidemic__Group_4_3__0 : rule__PhysicalEpidemic__Group_4_3__0__Impl rule__PhysicalEpidemic__Group_4_3__1 ;
    public final void rule__PhysicalEpidemic__Group_4_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:713:1: ( rule__PhysicalEpidemic__Group_4_3__0__Impl rule__PhysicalEpidemic__Group_4_3__1 )
            // InternalMyDsl.g:714:2: rule__PhysicalEpidemic__Group_4_3__0__Impl rule__PhysicalEpidemic__Group_4_3__1
            {
            pushFollow(FOLLOW_9);
            rule__PhysicalEpidemic__Group_4_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4_3__0"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4_3__0__Impl"
    // InternalMyDsl.g:721:1: rule__PhysicalEpidemic__Group_4_3__0__Impl : ( ',' ) ;
    public final void rule__PhysicalEpidemic__Group_4_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:725:1: ( ( ',' ) )
            // InternalMyDsl.g:726:1: ( ',' )
            {
            // InternalMyDsl.g:726:1: ( ',' )
            // InternalMyDsl.g:727:2: ','
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_4_3_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getPhysicalEpidemicAccess().getCommaKeyword_4_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4_3__0__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4_3__1"
    // InternalMyDsl.g:736:1: rule__PhysicalEpidemic__Group_4_3__1 : rule__PhysicalEpidemic__Group_4_3__1__Impl ;
    public final void rule__PhysicalEpidemic__Group_4_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:740:1: ( rule__PhysicalEpidemic__Group_4_3__1__Impl )
            // InternalMyDsl.g:741:2: rule__PhysicalEpidemic__Group_4_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__Group_4_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4_3__1"


    // $ANTLR start "rule__PhysicalEpidemic__Group_4_3__1__Impl"
    // InternalMyDsl.g:747:1: rule__PhysicalEpidemic__Group_4_3__1__Impl : ( ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 ) ) ;
    public final void rule__PhysicalEpidemic__Group_4_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:751:1: ( ( ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 ) ) )
            // InternalMyDsl.g:752:1: ( ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 ) )
            {
            // InternalMyDsl.g:752:1: ( ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 ) )
            // InternalMyDsl.g:753:2: ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 )
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getFlowsAssignment_4_3_1()); 
            // InternalMyDsl.g:754:2: ( rule__PhysicalEpidemic__FlowsAssignment_4_3_1 )
            // InternalMyDsl.g:754:3: rule__PhysicalEpidemic__FlowsAssignment_4_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalEpidemic__FlowsAssignment_4_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalEpidemicAccess().getFlowsAssignment_4_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__Group_4_3__1__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group__0"
    // InternalMyDsl.g:763:1: rule__PhysicalCompartment__Group__0 : rule__PhysicalCompartment__Group__0__Impl rule__PhysicalCompartment__Group__1 ;
    public final void rule__PhysicalCompartment__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:767:1: ( rule__PhysicalCompartment__Group__0__Impl rule__PhysicalCompartment__Group__1 )
            // InternalMyDsl.g:768:2: rule__PhysicalCompartment__Group__0__Impl rule__PhysicalCompartment__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__PhysicalCompartment__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__0"


    // $ANTLR start "rule__PhysicalCompartment__Group__0__Impl"
    // InternalMyDsl.g:775:1: rule__PhysicalCompartment__Group__0__Impl : ( () ) ;
    public final void rule__PhysicalCompartment__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:779:1: ( ( () ) )
            // InternalMyDsl.g:780:1: ( () )
            {
            // InternalMyDsl.g:780:1: ( () )
            // InternalMyDsl.g:781:2: ()
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentAction_0()); 
            // InternalMyDsl.g:782:2: ()
            // InternalMyDsl.g:782:3: 
            {
            }

             after(grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__0__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group__1"
    // InternalMyDsl.g:790:1: rule__PhysicalCompartment__Group__1 : rule__PhysicalCompartment__Group__1__Impl rule__PhysicalCompartment__Group__2 ;
    public final void rule__PhysicalCompartment__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:794:1: ( rule__PhysicalCompartment__Group__1__Impl rule__PhysicalCompartment__Group__2 )
            // InternalMyDsl.g:795:2: rule__PhysicalCompartment__Group__1__Impl rule__PhysicalCompartment__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalCompartment__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__1"


    // $ANTLR start "rule__PhysicalCompartment__Group__1__Impl"
    // InternalMyDsl.g:802:1: rule__PhysicalCompartment__Group__1__Impl : ( 'PhysicalCompartment' ) ;
    public final void rule__PhysicalCompartment__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:806:1: ( ( 'PhysicalCompartment' ) )
            // InternalMyDsl.g:807:1: ( 'PhysicalCompartment' )
            {
            // InternalMyDsl.g:807:1: ( 'PhysicalCompartment' )
            // InternalMyDsl.g:808:2: 'PhysicalCompartment'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentKeyword_1()); 
            match(input,17,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getPhysicalCompartmentKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__1__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group__2"
    // InternalMyDsl.g:817:1: rule__PhysicalCompartment__Group__2 : rule__PhysicalCompartment__Group__2__Impl rule__PhysicalCompartment__Group__3 ;
    public final void rule__PhysicalCompartment__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:821:1: ( rule__PhysicalCompartment__Group__2__Impl rule__PhysicalCompartment__Group__3 )
            // InternalMyDsl.g:822:2: rule__PhysicalCompartment__Group__2__Impl rule__PhysicalCompartment__Group__3
            {
            pushFollow(FOLLOW_10);
            rule__PhysicalCompartment__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__2"


    // $ANTLR start "rule__PhysicalCompartment__Group__2__Impl"
    // InternalMyDsl.g:829:1: rule__PhysicalCompartment__Group__2__Impl : ( '{' ) ;
    public final void rule__PhysicalCompartment__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:833:1: ( ( '{' ) )
            // InternalMyDsl.g:834:1: ( '{' )
            {
            // InternalMyDsl.g:834:1: ( '{' )
            // InternalMyDsl.g:835:2: '{'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__2__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group__3"
    // InternalMyDsl.g:844:1: rule__PhysicalCompartment__Group__3 : rule__PhysicalCompartment__Group__3__Impl rule__PhysicalCompartment__Group__4 ;
    public final void rule__PhysicalCompartment__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:848:1: ( rule__PhysicalCompartment__Group__3__Impl rule__PhysicalCompartment__Group__4 )
            // InternalMyDsl.g:849:2: rule__PhysicalCompartment__Group__3__Impl rule__PhysicalCompartment__Group__4
            {
            pushFollow(FOLLOW_10);
            rule__PhysicalCompartment__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__3"


    // $ANTLR start "rule__PhysicalCompartment__Group__3__Impl"
    // InternalMyDsl.g:856:1: rule__PhysicalCompartment__Group__3__Impl : ( ( rule__PhysicalCompartment__Group_3__0 )? ) ;
    public final void rule__PhysicalCompartment__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:860:1: ( ( ( rule__PhysicalCompartment__Group_3__0 )? ) )
            // InternalMyDsl.g:861:1: ( ( rule__PhysicalCompartment__Group_3__0 )? )
            {
            // InternalMyDsl.g:861:1: ( ( rule__PhysicalCompartment__Group_3__0 )? )
            // InternalMyDsl.g:862:2: ( rule__PhysicalCompartment__Group_3__0 )?
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getGroup_3()); 
            // InternalMyDsl.g:863:2: ( rule__PhysicalCompartment__Group_3__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalMyDsl.g:863:3: rule__PhysicalCompartment__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalCompartment__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalCompartmentAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__3__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group__4"
    // InternalMyDsl.g:871:1: rule__PhysicalCompartment__Group__4 : rule__PhysicalCompartment__Group__4__Impl ;
    public final void rule__PhysicalCompartment__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:875:1: ( rule__PhysicalCompartment__Group__4__Impl )
            // InternalMyDsl.g:876:2: rule__PhysicalCompartment__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__4"


    // $ANTLR start "rule__PhysicalCompartment__Group__4__Impl"
    // InternalMyDsl.g:882:1: rule__PhysicalCompartment__Group__4__Impl : ( '}' ) ;
    public final void rule__PhysicalCompartment__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:886:1: ( ( '}' ) )
            // InternalMyDsl.g:887:1: ( '}' )
            {
            // InternalMyDsl.g:887:1: ( '}' )
            // InternalMyDsl.g:888:2: '}'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_4()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group__4__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__0"
    // InternalMyDsl.g:898:1: rule__PhysicalCompartment__Group_3__0 : rule__PhysicalCompartment__Group_3__0__Impl rule__PhysicalCompartment__Group_3__1 ;
    public final void rule__PhysicalCompartment__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:902:1: ( rule__PhysicalCompartment__Group_3__0__Impl rule__PhysicalCompartment__Group_3__1 )
            // InternalMyDsl.g:903:2: rule__PhysicalCompartment__Group_3__0__Impl rule__PhysicalCompartment__Group_3__1
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalCompartment__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__0"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__0__Impl"
    // InternalMyDsl.g:910:1: rule__PhysicalCompartment__Group_3__0__Impl : ( 'labels' ) ;
    public final void rule__PhysicalCompartment__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:914:1: ( ( 'labels' ) )
            // InternalMyDsl.g:915:1: ( 'labels' )
            {
            // InternalMyDsl.g:915:1: ( 'labels' )
            // InternalMyDsl.g:916:2: 'labels'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLabelsKeyword_3_0()); 
            match(input,18,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getLabelsKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__0__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__1"
    // InternalMyDsl.g:925:1: rule__PhysicalCompartment__Group_3__1 : rule__PhysicalCompartment__Group_3__1__Impl rule__PhysicalCompartment__Group_3__2 ;
    public final void rule__PhysicalCompartment__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:929:1: ( rule__PhysicalCompartment__Group_3__1__Impl rule__PhysicalCompartment__Group_3__2 )
            // InternalMyDsl.g:930:2: rule__PhysicalCompartment__Group_3__1__Impl rule__PhysicalCompartment__Group_3__2
            {
            pushFollow(FOLLOW_11);
            rule__PhysicalCompartment__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__1"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__1__Impl"
    // InternalMyDsl.g:937:1: rule__PhysicalCompartment__Group_3__1__Impl : ( '{' ) ;
    public final void rule__PhysicalCompartment__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:941:1: ( ( '{' ) )
            // InternalMyDsl.g:942:1: ( '{' )
            {
            // InternalMyDsl.g:942:1: ( '{' )
            // InternalMyDsl.g:943:2: '{'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_3_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getLeftCurlyBracketKeyword_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__1__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__2"
    // InternalMyDsl.g:952:1: rule__PhysicalCompartment__Group_3__2 : rule__PhysicalCompartment__Group_3__2__Impl rule__PhysicalCompartment__Group_3__3 ;
    public final void rule__PhysicalCompartment__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:956:1: ( rule__PhysicalCompartment__Group_3__2__Impl rule__PhysicalCompartment__Group_3__3 )
            // InternalMyDsl.g:957:2: rule__PhysicalCompartment__Group_3__2__Impl rule__PhysicalCompartment__Group_3__3
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalCompartment__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__2"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__2__Impl"
    // InternalMyDsl.g:964:1: rule__PhysicalCompartment__Group_3__2__Impl : ( ( rule__PhysicalCompartment__LabelsAssignment_3_2 ) ) ;
    public final void rule__PhysicalCompartment__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:968:1: ( ( ( rule__PhysicalCompartment__LabelsAssignment_3_2 ) ) )
            // InternalMyDsl.g:969:1: ( ( rule__PhysicalCompartment__LabelsAssignment_3_2 ) )
            {
            // InternalMyDsl.g:969:1: ( ( rule__PhysicalCompartment__LabelsAssignment_3_2 ) )
            // InternalMyDsl.g:970:2: ( rule__PhysicalCompartment__LabelsAssignment_3_2 )
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLabelsAssignment_3_2()); 
            // InternalMyDsl.g:971:2: ( rule__PhysicalCompartment__LabelsAssignment_3_2 )
            // InternalMyDsl.g:971:3: rule__PhysicalCompartment__LabelsAssignment_3_2
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__LabelsAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalCompartmentAccess().getLabelsAssignment_3_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__2__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__3"
    // InternalMyDsl.g:979:1: rule__PhysicalCompartment__Group_3__3 : rule__PhysicalCompartment__Group_3__3__Impl rule__PhysicalCompartment__Group_3__4 ;
    public final void rule__PhysicalCompartment__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:983:1: ( rule__PhysicalCompartment__Group_3__3__Impl rule__PhysicalCompartment__Group_3__4 )
            // InternalMyDsl.g:984:2: rule__PhysicalCompartment__Group_3__3__Impl rule__PhysicalCompartment__Group_3__4
            {
            pushFollow(FOLLOW_7);
            rule__PhysicalCompartment__Group_3__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__3"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__3__Impl"
    // InternalMyDsl.g:991:1: rule__PhysicalCompartment__Group_3__3__Impl : ( ( rule__PhysicalCompartment__Group_3_3__0 )* ) ;
    public final void rule__PhysicalCompartment__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:995:1: ( ( ( rule__PhysicalCompartment__Group_3_3__0 )* ) )
            // InternalMyDsl.g:996:1: ( ( rule__PhysicalCompartment__Group_3_3__0 )* )
            {
            // InternalMyDsl.g:996:1: ( ( rule__PhysicalCompartment__Group_3_3__0 )* )
            // InternalMyDsl.g:997:2: ( rule__PhysicalCompartment__Group_3_3__0 )*
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getGroup_3_3()); 
            // InternalMyDsl.g:998:2: ( rule__PhysicalCompartment__Group_3_3__0 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==15) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalMyDsl.g:998:3: rule__PhysicalCompartment__Group_3_3__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__PhysicalCompartment__Group_3_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

             after(grammarAccess.getPhysicalCompartmentAccess().getGroup_3_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__3__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__4"
    // InternalMyDsl.g:1006:1: rule__PhysicalCompartment__Group_3__4 : rule__PhysicalCompartment__Group_3__4__Impl ;
    public final void rule__PhysicalCompartment__Group_3__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1010:1: ( rule__PhysicalCompartment__Group_3__4__Impl )
            // InternalMyDsl.g:1011:2: rule__PhysicalCompartment__Group_3__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__4"


    // $ANTLR start "rule__PhysicalCompartment__Group_3__4__Impl"
    // InternalMyDsl.g:1017:1: rule__PhysicalCompartment__Group_3__4__Impl : ( '}' ) ;
    public final void rule__PhysicalCompartment__Group_3__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1021:1: ( ( '}' ) )
            // InternalMyDsl.g:1022:1: ( '}' )
            {
            // InternalMyDsl.g:1022:1: ( '}' )
            // InternalMyDsl.g:1023:2: '}'
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_3_4()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getRightCurlyBracketKeyword_3_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3__4__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3_3__0"
    // InternalMyDsl.g:1033:1: rule__PhysicalCompartment__Group_3_3__0 : rule__PhysicalCompartment__Group_3_3__0__Impl rule__PhysicalCompartment__Group_3_3__1 ;
    public final void rule__PhysicalCompartment__Group_3_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1037:1: ( rule__PhysicalCompartment__Group_3_3__0__Impl rule__PhysicalCompartment__Group_3_3__1 )
            // InternalMyDsl.g:1038:2: rule__PhysicalCompartment__Group_3_3__0__Impl rule__PhysicalCompartment__Group_3_3__1
            {
            pushFollow(FOLLOW_11);
            rule__PhysicalCompartment__Group_3_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3_3__0"


    // $ANTLR start "rule__PhysicalCompartment__Group_3_3__0__Impl"
    // InternalMyDsl.g:1045:1: rule__PhysicalCompartment__Group_3_3__0__Impl : ( ',' ) ;
    public final void rule__PhysicalCompartment__Group_3_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1049:1: ( ( ',' ) )
            // InternalMyDsl.g:1050:1: ( ',' )
            {
            // InternalMyDsl.g:1050:1: ( ',' )
            // InternalMyDsl.g:1051:2: ','
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getCommaKeyword_3_3_0()); 
            match(input,15,FOLLOW_2); 
             after(grammarAccess.getPhysicalCompartmentAccess().getCommaKeyword_3_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3_3__0__Impl"


    // $ANTLR start "rule__PhysicalCompartment__Group_3_3__1"
    // InternalMyDsl.g:1060:1: rule__PhysicalCompartment__Group_3_3__1 : rule__PhysicalCompartment__Group_3_3__1__Impl ;
    public final void rule__PhysicalCompartment__Group_3_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1064:1: ( rule__PhysicalCompartment__Group_3_3__1__Impl )
            // InternalMyDsl.g:1065:2: rule__PhysicalCompartment__Group_3_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__Group_3_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3_3__1"


    // $ANTLR start "rule__PhysicalCompartment__Group_3_3__1__Impl"
    // InternalMyDsl.g:1071:1: rule__PhysicalCompartment__Group_3_3__1__Impl : ( ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 ) ) ;
    public final void rule__PhysicalCompartment__Group_3_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1075:1: ( ( ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 ) ) )
            // InternalMyDsl.g:1076:1: ( ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 ) )
            {
            // InternalMyDsl.g:1076:1: ( ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 ) )
            // InternalMyDsl.g:1077:2: ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 )
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLabelsAssignment_3_3_1()); 
            // InternalMyDsl.g:1078:2: ( rule__PhysicalCompartment__LabelsAssignment_3_3_1 )
            // InternalMyDsl.g:1078:3: rule__PhysicalCompartment__LabelsAssignment_3_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalCompartment__LabelsAssignment_3_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalCompartmentAccess().getLabelsAssignment_3_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__Group_3_3__1__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__0"
    // InternalMyDsl.g:1087:1: rule__PhysicalFlow__Group__0 : rule__PhysicalFlow__Group__0__Impl rule__PhysicalFlow__Group__1 ;
    public final void rule__PhysicalFlow__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1091:1: ( rule__PhysicalFlow__Group__0__Impl rule__PhysicalFlow__Group__1 )
            // InternalMyDsl.g:1092:2: rule__PhysicalFlow__Group__0__Impl rule__PhysicalFlow__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__PhysicalFlow__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__0"


    // $ANTLR start "rule__PhysicalFlow__Group__0__Impl"
    // InternalMyDsl.g:1099:1: rule__PhysicalFlow__Group__0__Impl : ( 'PhysicalFlow' ) ;
    public final void rule__PhysicalFlow__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1103:1: ( ( 'PhysicalFlow' ) )
            // InternalMyDsl.g:1104:1: ( 'PhysicalFlow' )
            {
            // InternalMyDsl.g:1104:1: ( 'PhysicalFlow' )
            // InternalMyDsl.g:1105:2: 'PhysicalFlow'
            {
             before(grammarAccess.getPhysicalFlowAccess().getPhysicalFlowKeyword_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getPhysicalFlowKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__0__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__1"
    // InternalMyDsl.g:1114:1: rule__PhysicalFlow__Group__1 : rule__PhysicalFlow__Group__1__Impl rule__PhysicalFlow__Group__2 ;
    public final void rule__PhysicalFlow__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1118:1: ( rule__PhysicalFlow__Group__1__Impl rule__PhysicalFlow__Group__2 )
            // InternalMyDsl.g:1119:2: rule__PhysicalFlow__Group__1__Impl rule__PhysicalFlow__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__PhysicalFlow__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__1"


    // $ANTLR start "rule__PhysicalFlow__Group__1__Impl"
    // InternalMyDsl.g:1126:1: rule__PhysicalFlow__Group__1__Impl : ( '{' ) ;
    public final void rule__PhysicalFlow__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1130:1: ( ( '{' ) )
            // InternalMyDsl.g:1131:1: ( '{' )
            {
            // InternalMyDsl.g:1131:1: ( '{' )
            // InternalMyDsl.g:1132:2: '{'
            {
             before(grammarAccess.getPhysicalFlowAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__1__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__2"
    // InternalMyDsl.g:1141:1: rule__PhysicalFlow__Group__2 : rule__PhysicalFlow__Group__2__Impl rule__PhysicalFlow__Group__3 ;
    public final void rule__PhysicalFlow__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1145:1: ( rule__PhysicalFlow__Group__2__Impl rule__PhysicalFlow__Group__3 )
            // InternalMyDsl.g:1146:2: rule__PhysicalFlow__Group__2__Impl rule__PhysicalFlow__Group__3
            {
            pushFollow(FOLLOW_12);
            rule__PhysicalFlow__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__2"


    // $ANTLR start "rule__PhysicalFlow__Group__2__Impl"
    // InternalMyDsl.g:1153:1: rule__PhysicalFlow__Group__2__Impl : ( ( rule__PhysicalFlow__Group_2__0 )? ) ;
    public final void rule__PhysicalFlow__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1157:1: ( ( ( rule__PhysicalFlow__Group_2__0 )? ) )
            // InternalMyDsl.g:1158:1: ( ( rule__PhysicalFlow__Group_2__0 )? )
            {
            // InternalMyDsl.g:1158:1: ( ( rule__PhysicalFlow__Group_2__0 )? )
            // InternalMyDsl.g:1159:2: ( rule__PhysicalFlow__Group_2__0 )?
            {
             before(grammarAccess.getPhysicalFlowAccess().getGroup_2()); 
            // InternalMyDsl.g:1160:2: ( rule__PhysicalFlow__Group_2__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==21) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalMyDsl.g:1160:3: rule__PhysicalFlow__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalFlow__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalFlowAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__2__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__3"
    // InternalMyDsl.g:1168:1: rule__PhysicalFlow__Group__3 : rule__PhysicalFlow__Group__3__Impl rule__PhysicalFlow__Group__4 ;
    public final void rule__PhysicalFlow__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1172:1: ( rule__PhysicalFlow__Group__3__Impl rule__PhysicalFlow__Group__4 )
            // InternalMyDsl.g:1173:2: rule__PhysicalFlow__Group__3__Impl rule__PhysicalFlow__Group__4
            {
            pushFollow(FOLLOW_12);
            rule__PhysicalFlow__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__3"


    // $ANTLR start "rule__PhysicalFlow__Group__3__Impl"
    // InternalMyDsl.g:1180:1: rule__PhysicalFlow__Group__3__Impl : ( ( rule__PhysicalFlow__Group_3__0 )? ) ;
    public final void rule__PhysicalFlow__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1184:1: ( ( ( rule__PhysicalFlow__Group_3__0 )? ) )
            // InternalMyDsl.g:1185:1: ( ( rule__PhysicalFlow__Group_3__0 )? )
            {
            // InternalMyDsl.g:1185:1: ( ( rule__PhysicalFlow__Group_3__0 )? )
            // InternalMyDsl.g:1186:2: ( rule__PhysicalFlow__Group_3__0 )?
            {
             before(grammarAccess.getPhysicalFlowAccess().getGroup_3()); 
            // InternalMyDsl.g:1187:2: ( rule__PhysicalFlow__Group_3__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==22) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalMyDsl.g:1187:3: rule__PhysicalFlow__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalFlow__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalFlowAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__3__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__4"
    // InternalMyDsl.g:1195:1: rule__PhysicalFlow__Group__4 : rule__PhysicalFlow__Group__4__Impl rule__PhysicalFlow__Group__5 ;
    public final void rule__PhysicalFlow__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1199:1: ( rule__PhysicalFlow__Group__4__Impl rule__PhysicalFlow__Group__5 )
            // InternalMyDsl.g:1200:2: rule__PhysicalFlow__Group__4__Impl rule__PhysicalFlow__Group__5
            {
            pushFollow(FOLLOW_12);
            rule__PhysicalFlow__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__4"


    // $ANTLR start "rule__PhysicalFlow__Group__4__Impl"
    // InternalMyDsl.g:1207:1: rule__PhysicalFlow__Group__4__Impl : ( ( rule__PhysicalFlow__Group_4__0 )? ) ;
    public final void rule__PhysicalFlow__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1211:1: ( ( ( rule__PhysicalFlow__Group_4__0 )? ) )
            // InternalMyDsl.g:1212:1: ( ( rule__PhysicalFlow__Group_4__0 )? )
            {
            // InternalMyDsl.g:1212:1: ( ( rule__PhysicalFlow__Group_4__0 )? )
            // InternalMyDsl.g:1213:2: ( rule__PhysicalFlow__Group_4__0 )?
            {
             before(grammarAccess.getPhysicalFlowAccess().getGroup_4()); 
            // InternalMyDsl.g:1214:2: ( rule__PhysicalFlow__Group_4__0 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==23) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMyDsl.g:1214:3: rule__PhysicalFlow__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PhysicalFlow__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhysicalFlowAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__4__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__5"
    // InternalMyDsl.g:1222:1: rule__PhysicalFlow__Group__5 : rule__PhysicalFlow__Group__5__Impl rule__PhysicalFlow__Group__6 ;
    public final void rule__PhysicalFlow__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1226:1: ( rule__PhysicalFlow__Group__5__Impl rule__PhysicalFlow__Group__6 )
            // InternalMyDsl.g:1227:2: rule__PhysicalFlow__Group__5__Impl rule__PhysicalFlow__Group__6
            {
            pushFollow(FOLLOW_13);
            rule__PhysicalFlow__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__5"


    // $ANTLR start "rule__PhysicalFlow__Group__5__Impl"
    // InternalMyDsl.g:1234:1: rule__PhysicalFlow__Group__5__Impl : ( 'equationtemplate' ) ;
    public final void rule__PhysicalFlow__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1238:1: ( ( 'equationtemplate' ) )
            // InternalMyDsl.g:1239:1: ( 'equationtemplate' )
            {
            // InternalMyDsl.g:1239:1: ( 'equationtemplate' )
            // InternalMyDsl.g:1240:2: 'equationtemplate'
            {
             before(grammarAccess.getPhysicalFlowAccess().getEquationtemplateKeyword_5()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getEquationtemplateKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__5__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__6"
    // InternalMyDsl.g:1249:1: rule__PhysicalFlow__Group__6 : rule__PhysicalFlow__Group__6__Impl rule__PhysicalFlow__Group__7 ;
    public final void rule__PhysicalFlow__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1253:1: ( rule__PhysicalFlow__Group__6__Impl rule__PhysicalFlow__Group__7 )
            // InternalMyDsl.g:1254:2: rule__PhysicalFlow__Group__6__Impl rule__PhysicalFlow__Group__7
            {
            pushFollow(FOLLOW_14);
            rule__PhysicalFlow__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__6"


    // $ANTLR start "rule__PhysicalFlow__Group__6__Impl"
    // InternalMyDsl.g:1261:1: rule__PhysicalFlow__Group__6__Impl : ( ( rule__PhysicalFlow__EquationtemplateAssignment_6 ) ) ;
    public final void rule__PhysicalFlow__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1265:1: ( ( ( rule__PhysicalFlow__EquationtemplateAssignment_6 ) ) )
            // InternalMyDsl.g:1266:1: ( ( rule__PhysicalFlow__EquationtemplateAssignment_6 ) )
            {
            // InternalMyDsl.g:1266:1: ( ( rule__PhysicalFlow__EquationtemplateAssignment_6 ) )
            // InternalMyDsl.g:1267:2: ( rule__PhysicalFlow__EquationtemplateAssignment_6 )
            {
             before(grammarAccess.getPhysicalFlowAccess().getEquationtemplateAssignment_6()); 
            // InternalMyDsl.g:1268:2: ( rule__PhysicalFlow__EquationtemplateAssignment_6 )
            // InternalMyDsl.g:1268:3: rule__PhysicalFlow__EquationtemplateAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__EquationtemplateAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalFlowAccess().getEquationtemplateAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__6__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group__7"
    // InternalMyDsl.g:1276:1: rule__PhysicalFlow__Group__7 : rule__PhysicalFlow__Group__7__Impl ;
    public final void rule__PhysicalFlow__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1280:1: ( rule__PhysicalFlow__Group__7__Impl )
            // InternalMyDsl.g:1281:2: rule__PhysicalFlow__Group__7__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group__7__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__7"


    // $ANTLR start "rule__PhysicalFlow__Group__7__Impl"
    // InternalMyDsl.g:1287:1: rule__PhysicalFlow__Group__7__Impl : ( '}' ) ;
    public final void rule__PhysicalFlow__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1291:1: ( ( '}' ) )
            // InternalMyDsl.g:1292:1: ( '}' )
            {
            // InternalMyDsl.g:1292:1: ( '}' )
            // InternalMyDsl.g:1293:2: '}'
            {
             before(grammarAccess.getPhysicalFlowAccess().getRightCurlyBracketKeyword_7()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getRightCurlyBracketKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group__7__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_2__0"
    // InternalMyDsl.g:1303:1: rule__PhysicalFlow__Group_2__0 : rule__PhysicalFlow__Group_2__0__Impl rule__PhysicalFlow__Group_2__1 ;
    public final void rule__PhysicalFlow__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1307:1: ( rule__PhysicalFlow__Group_2__0__Impl rule__PhysicalFlow__Group_2__1 )
            // InternalMyDsl.g:1308:2: rule__PhysicalFlow__Group_2__0__Impl rule__PhysicalFlow__Group_2__1
            {
            pushFollow(FOLLOW_15);
            rule__PhysicalFlow__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_2__0"


    // $ANTLR start "rule__PhysicalFlow__Group_2__0__Impl"
    // InternalMyDsl.g:1315:1: rule__PhysicalFlow__Group_2__0__Impl : ( 'id' ) ;
    public final void rule__PhysicalFlow__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1319:1: ( ( 'id' ) )
            // InternalMyDsl.g:1320:1: ( 'id' )
            {
            // InternalMyDsl.g:1320:1: ( 'id' )
            // InternalMyDsl.g:1321:2: 'id'
            {
             before(grammarAccess.getPhysicalFlowAccess().getIdKeyword_2_0()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getIdKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_2__0__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_2__1"
    // InternalMyDsl.g:1330:1: rule__PhysicalFlow__Group_2__1 : rule__PhysicalFlow__Group_2__1__Impl ;
    public final void rule__PhysicalFlow__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1334:1: ( rule__PhysicalFlow__Group_2__1__Impl )
            // InternalMyDsl.g:1335:2: rule__PhysicalFlow__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_2__1"


    // $ANTLR start "rule__PhysicalFlow__Group_2__1__Impl"
    // InternalMyDsl.g:1341:1: rule__PhysicalFlow__Group_2__1__Impl : ( ( rule__PhysicalFlow__IdAssignment_2_1 ) ) ;
    public final void rule__PhysicalFlow__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1345:1: ( ( ( rule__PhysicalFlow__IdAssignment_2_1 ) ) )
            // InternalMyDsl.g:1346:1: ( ( rule__PhysicalFlow__IdAssignment_2_1 ) )
            {
            // InternalMyDsl.g:1346:1: ( ( rule__PhysicalFlow__IdAssignment_2_1 ) )
            // InternalMyDsl.g:1347:2: ( rule__PhysicalFlow__IdAssignment_2_1 )
            {
             before(grammarAccess.getPhysicalFlowAccess().getIdAssignment_2_1()); 
            // InternalMyDsl.g:1348:2: ( rule__PhysicalFlow__IdAssignment_2_1 )
            // InternalMyDsl.g:1348:3: rule__PhysicalFlow__IdAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__IdAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalFlowAccess().getIdAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_2__1__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_3__0"
    // InternalMyDsl.g:1357:1: rule__PhysicalFlow__Group_3__0 : rule__PhysicalFlow__Group_3__0__Impl rule__PhysicalFlow__Group_3__1 ;
    public final void rule__PhysicalFlow__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1361:1: ( rule__PhysicalFlow__Group_3__0__Impl rule__PhysicalFlow__Group_3__1 )
            // InternalMyDsl.g:1362:2: rule__PhysicalFlow__Group_3__0__Impl rule__PhysicalFlow__Group_3__1
            {
            pushFollow(FOLLOW_15);
            rule__PhysicalFlow__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_3__0"


    // $ANTLR start "rule__PhysicalFlow__Group_3__0__Impl"
    // InternalMyDsl.g:1369:1: rule__PhysicalFlow__Group_3__0__Impl : ( 'from' ) ;
    public final void rule__PhysicalFlow__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1373:1: ( ( 'from' ) )
            // InternalMyDsl.g:1374:1: ( 'from' )
            {
            // InternalMyDsl.g:1374:1: ( 'from' )
            // InternalMyDsl.g:1375:2: 'from'
            {
             before(grammarAccess.getPhysicalFlowAccess().getFromKeyword_3_0()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getFromKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_3__0__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_3__1"
    // InternalMyDsl.g:1384:1: rule__PhysicalFlow__Group_3__1 : rule__PhysicalFlow__Group_3__1__Impl ;
    public final void rule__PhysicalFlow__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1388:1: ( rule__PhysicalFlow__Group_3__1__Impl )
            // InternalMyDsl.g:1389:2: rule__PhysicalFlow__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_3__1"


    // $ANTLR start "rule__PhysicalFlow__Group_3__1__Impl"
    // InternalMyDsl.g:1395:1: rule__PhysicalFlow__Group_3__1__Impl : ( ( rule__PhysicalFlow__FromAssignment_3_1 ) ) ;
    public final void rule__PhysicalFlow__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1399:1: ( ( ( rule__PhysicalFlow__FromAssignment_3_1 ) ) )
            // InternalMyDsl.g:1400:1: ( ( rule__PhysicalFlow__FromAssignment_3_1 ) )
            {
            // InternalMyDsl.g:1400:1: ( ( rule__PhysicalFlow__FromAssignment_3_1 ) )
            // InternalMyDsl.g:1401:2: ( rule__PhysicalFlow__FromAssignment_3_1 )
            {
             before(grammarAccess.getPhysicalFlowAccess().getFromAssignment_3_1()); 
            // InternalMyDsl.g:1402:2: ( rule__PhysicalFlow__FromAssignment_3_1 )
            // InternalMyDsl.g:1402:3: rule__PhysicalFlow__FromAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__FromAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalFlowAccess().getFromAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_3__1__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_4__0"
    // InternalMyDsl.g:1411:1: rule__PhysicalFlow__Group_4__0 : rule__PhysicalFlow__Group_4__0__Impl rule__PhysicalFlow__Group_4__1 ;
    public final void rule__PhysicalFlow__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1415:1: ( rule__PhysicalFlow__Group_4__0__Impl rule__PhysicalFlow__Group_4__1 )
            // InternalMyDsl.g:1416:2: rule__PhysicalFlow__Group_4__0__Impl rule__PhysicalFlow__Group_4__1
            {
            pushFollow(FOLLOW_15);
            rule__PhysicalFlow__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_4__0"


    // $ANTLR start "rule__PhysicalFlow__Group_4__0__Impl"
    // InternalMyDsl.g:1423:1: rule__PhysicalFlow__Group_4__0__Impl : ( 'to' ) ;
    public final void rule__PhysicalFlow__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1427:1: ( ( 'to' ) )
            // InternalMyDsl.g:1428:1: ( 'to' )
            {
            // InternalMyDsl.g:1428:1: ( 'to' )
            // InternalMyDsl.g:1429:2: 'to'
            {
             before(grammarAccess.getPhysicalFlowAccess().getToKeyword_4_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getPhysicalFlowAccess().getToKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_4__0__Impl"


    // $ANTLR start "rule__PhysicalFlow__Group_4__1"
    // InternalMyDsl.g:1438:1: rule__PhysicalFlow__Group_4__1 : rule__PhysicalFlow__Group_4__1__Impl ;
    public final void rule__PhysicalFlow__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1442:1: ( rule__PhysicalFlow__Group_4__1__Impl )
            // InternalMyDsl.g:1443:2: rule__PhysicalFlow__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_4__1"


    // $ANTLR start "rule__PhysicalFlow__Group_4__1__Impl"
    // InternalMyDsl.g:1449:1: rule__PhysicalFlow__Group_4__1__Impl : ( ( rule__PhysicalFlow__ToAssignment_4_1 ) ) ;
    public final void rule__PhysicalFlow__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1453:1: ( ( ( rule__PhysicalFlow__ToAssignment_4_1 ) ) )
            // InternalMyDsl.g:1454:1: ( ( rule__PhysicalFlow__ToAssignment_4_1 ) )
            {
            // InternalMyDsl.g:1454:1: ( ( rule__PhysicalFlow__ToAssignment_4_1 ) )
            // InternalMyDsl.g:1455:2: ( rule__PhysicalFlow__ToAssignment_4_1 )
            {
             before(grammarAccess.getPhysicalFlowAccess().getToAssignment_4_1()); 
            // InternalMyDsl.g:1456:2: ( rule__PhysicalFlow__ToAssignment_4_1 )
            // InternalMyDsl.g:1456:3: rule__PhysicalFlow__ToAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__PhysicalFlow__ToAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getPhysicalFlowAccess().getToAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__Group_4__1__Impl"


    // $ANTLR start "rule__Label__Group__0"
    // InternalMyDsl.g:1465:1: rule__Label__Group__0 : rule__Label__Group__0__Impl rule__Label__Group__1 ;
    public final void rule__Label__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1469:1: ( rule__Label__Group__0__Impl rule__Label__Group__1 )
            // InternalMyDsl.g:1470:2: rule__Label__Group__0__Impl rule__Label__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__Label__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Label__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__0"


    // $ANTLR start "rule__Label__Group__0__Impl"
    // InternalMyDsl.g:1477:1: rule__Label__Group__0__Impl : ( () ) ;
    public final void rule__Label__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1481:1: ( ( () ) )
            // InternalMyDsl.g:1482:1: ( () )
            {
            // InternalMyDsl.g:1482:1: ( () )
            // InternalMyDsl.g:1483:2: ()
            {
             before(grammarAccess.getLabelAccess().getLabelAction_0()); 
            // InternalMyDsl.g:1484:2: ()
            // InternalMyDsl.g:1484:3: 
            {
            }

             after(grammarAccess.getLabelAccess().getLabelAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__0__Impl"


    // $ANTLR start "rule__Label__Group__1"
    // InternalMyDsl.g:1492:1: rule__Label__Group__1 : rule__Label__Group__1__Impl rule__Label__Group__2 ;
    public final void rule__Label__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1496:1: ( rule__Label__Group__1__Impl rule__Label__Group__2 )
            // InternalMyDsl.g:1497:2: rule__Label__Group__1__Impl rule__Label__Group__2
            {
            pushFollow(FOLLOW_15);
            rule__Label__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Label__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__1"


    // $ANTLR start "rule__Label__Group__1__Impl"
    // InternalMyDsl.g:1504:1: rule__Label__Group__1__Impl : ( 'Label' ) ;
    public final void rule__Label__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1508:1: ( ( 'Label' ) )
            // InternalMyDsl.g:1509:1: ( 'Label' )
            {
            // InternalMyDsl.g:1509:1: ( 'Label' )
            // InternalMyDsl.g:1510:2: 'Label'
            {
             before(grammarAccess.getLabelAccess().getLabelKeyword_1()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getLabelAccess().getLabelKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__1__Impl"


    // $ANTLR start "rule__Label__Group__2"
    // InternalMyDsl.g:1519:1: rule__Label__Group__2 : rule__Label__Group__2__Impl ;
    public final void rule__Label__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1523:1: ( rule__Label__Group__2__Impl )
            // InternalMyDsl.g:1524:2: rule__Label__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Label__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__2"


    // $ANTLR start "rule__Label__Group__2__Impl"
    // InternalMyDsl.g:1530:1: rule__Label__Group__2__Impl : ( ( rule__Label__NameAssignment_2 ) ) ;
    public final void rule__Label__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1534:1: ( ( ( rule__Label__NameAssignment_2 ) ) )
            // InternalMyDsl.g:1535:1: ( ( rule__Label__NameAssignment_2 ) )
            {
            // InternalMyDsl.g:1535:1: ( ( rule__Label__NameAssignment_2 ) )
            // InternalMyDsl.g:1536:2: ( rule__Label__NameAssignment_2 )
            {
             before(grammarAccess.getLabelAccess().getNameAssignment_2()); 
            // InternalMyDsl.g:1537:2: ( rule__Label__NameAssignment_2 )
            // InternalMyDsl.g:1537:3: rule__Label__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Label__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getLabelAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__Group__2__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__0"
    // InternalMyDsl.g:1546:1: rule__EquationTemplate__Group__0 : rule__EquationTemplate__Group__0__Impl rule__EquationTemplate__Group__1 ;
    public final void rule__EquationTemplate__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1550:1: ( rule__EquationTemplate__Group__0__Impl rule__EquationTemplate__Group__1 )
            // InternalMyDsl.g:1551:2: rule__EquationTemplate__Group__0__Impl rule__EquationTemplate__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__EquationTemplate__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__0"


    // $ANTLR start "rule__EquationTemplate__Group__0__Impl"
    // InternalMyDsl.g:1558:1: rule__EquationTemplate__Group__0__Impl : ( () ) ;
    public final void rule__EquationTemplate__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1562:1: ( ( () ) )
            // InternalMyDsl.g:1563:1: ( () )
            {
            // InternalMyDsl.g:1563:1: ( () )
            // InternalMyDsl.g:1564:2: ()
            {
             before(grammarAccess.getEquationTemplateAccess().getEquationTemplateAction_0()); 
            // InternalMyDsl.g:1565:2: ()
            // InternalMyDsl.g:1565:3: 
            {
            }

             after(grammarAccess.getEquationTemplateAccess().getEquationTemplateAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__0__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__1"
    // InternalMyDsl.g:1573:1: rule__EquationTemplate__Group__1 : rule__EquationTemplate__Group__1__Impl rule__EquationTemplate__Group__2 ;
    public final void rule__EquationTemplate__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1577:1: ( rule__EquationTemplate__Group__1__Impl rule__EquationTemplate__Group__2 )
            // InternalMyDsl.g:1578:2: rule__EquationTemplate__Group__1__Impl rule__EquationTemplate__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__EquationTemplate__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__1"


    // $ANTLR start "rule__EquationTemplate__Group__1__Impl"
    // InternalMyDsl.g:1585:1: rule__EquationTemplate__Group__1__Impl : ( 'EquationTemplate' ) ;
    public final void rule__EquationTemplate__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1589:1: ( ( 'EquationTemplate' ) )
            // InternalMyDsl.g:1590:1: ( 'EquationTemplate' )
            {
            // InternalMyDsl.g:1590:1: ( 'EquationTemplate' )
            // InternalMyDsl.g:1591:2: 'EquationTemplate'
            {
             before(grammarAccess.getEquationTemplateAccess().getEquationTemplateKeyword_1()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getEquationTemplateKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__1__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__2"
    // InternalMyDsl.g:1600:1: rule__EquationTemplate__Group__2 : rule__EquationTemplate__Group__2__Impl rule__EquationTemplate__Group__3 ;
    public final void rule__EquationTemplate__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1604:1: ( rule__EquationTemplate__Group__2__Impl rule__EquationTemplate__Group__3 )
            // InternalMyDsl.g:1605:2: rule__EquationTemplate__Group__2__Impl rule__EquationTemplate__Group__3
            {
            pushFollow(FOLLOW_16);
            rule__EquationTemplate__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__2"


    // $ANTLR start "rule__EquationTemplate__Group__2__Impl"
    // InternalMyDsl.g:1612:1: rule__EquationTemplate__Group__2__Impl : ( '{' ) ;
    public final void rule__EquationTemplate__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1616:1: ( ( '{' ) )
            // InternalMyDsl.g:1617:1: ( '{' )
            {
            // InternalMyDsl.g:1617:1: ( '{' )
            // InternalMyDsl.g:1618:2: '{'
            {
             before(grammarAccess.getEquationTemplateAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,12,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__2__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__3"
    // InternalMyDsl.g:1627:1: rule__EquationTemplate__Group__3 : rule__EquationTemplate__Group__3__Impl rule__EquationTemplate__Group__4 ;
    public final void rule__EquationTemplate__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1631:1: ( rule__EquationTemplate__Group__3__Impl rule__EquationTemplate__Group__4 )
            // InternalMyDsl.g:1632:2: rule__EquationTemplate__Group__3__Impl rule__EquationTemplate__Group__4
            {
            pushFollow(FOLLOW_16);
            rule__EquationTemplate__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__3"


    // $ANTLR start "rule__EquationTemplate__Group__3__Impl"
    // InternalMyDsl.g:1639:1: rule__EquationTemplate__Group__3__Impl : ( ( rule__EquationTemplate__Group_3__0 )? ) ;
    public final void rule__EquationTemplate__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1643:1: ( ( ( rule__EquationTemplate__Group_3__0 )? ) )
            // InternalMyDsl.g:1644:1: ( ( rule__EquationTemplate__Group_3__0 )? )
            {
            // InternalMyDsl.g:1644:1: ( ( rule__EquationTemplate__Group_3__0 )? )
            // InternalMyDsl.g:1645:2: ( rule__EquationTemplate__Group_3__0 )?
            {
             before(grammarAccess.getEquationTemplateAccess().getGroup_3()); 
            // InternalMyDsl.g:1646:2: ( rule__EquationTemplate__Group_3__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==26) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMyDsl.g:1646:3: rule__EquationTemplate__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__EquationTemplate__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEquationTemplateAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__3__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__4"
    // InternalMyDsl.g:1654:1: rule__EquationTemplate__Group__4 : rule__EquationTemplate__Group__4__Impl rule__EquationTemplate__Group__5 ;
    public final void rule__EquationTemplate__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1658:1: ( rule__EquationTemplate__Group__4__Impl rule__EquationTemplate__Group__5 )
            // InternalMyDsl.g:1659:2: rule__EquationTemplate__Group__4__Impl rule__EquationTemplate__Group__5
            {
            pushFollow(FOLLOW_16);
            rule__EquationTemplate__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__4"


    // $ANTLR start "rule__EquationTemplate__Group__4__Impl"
    // InternalMyDsl.g:1666:1: rule__EquationTemplate__Group__4__Impl : ( ( rule__EquationTemplate__Group_4__0 )? ) ;
    public final void rule__EquationTemplate__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1670:1: ( ( ( rule__EquationTemplate__Group_4__0 )? ) )
            // InternalMyDsl.g:1671:1: ( ( rule__EquationTemplate__Group_4__0 )? )
            {
            // InternalMyDsl.g:1671:1: ( ( rule__EquationTemplate__Group_4__0 )? )
            // InternalMyDsl.g:1672:2: ( rule__EquationTemplate__Group_4__0 )?
            {
             before(grammarAccess.getEquationTemplateAccess().getGroup_4()); 
            // InternalMyDsl.g:1673:2: ( rule__EquationTemplate__Group_4__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==27) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMyDsl.g:1673:3: rule__EquationTemplate__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__EquationTemplate__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEquationTemplateAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__4__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__5"
    // InternalMyDsl.g:1681:1: rule__EquationTemplate__Group__5 : rule__EquationTemplate__Group__5__Impl rule__EquationTemplate__Group__6 ;
    public final void rule__EquationTemplate__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1685:1: ( rule__EquationTemplate__Group__5__Impl rule__EquationTemplate__Group__6 )
            // InternalMyDsl.g:1686:2: rule__EquationTemplate__Group__5__Impl rule__EquationTemplate__Group__6
            {
            pushFollow(FOLLOW_16);
            rule__EquationTemplate__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__5"


    // $ANTLR start "rule__EquationTemplate__Group__5__Impl"
    // InternalMyDsl.g:1693:1: rule__EquationTemplate__Group__5__Impl : ( ( rule__EquationTemplate__Group_5__0 )? ) ;
    public final void rule__EquationTemplate__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1697:1: ( ( ( rule__EquationTemplate__Group_5__0 )? ) )
            // InternalMyDsl.g:1698:1: ( ( rule__EquationTemplate__Group_5__0 )? )
            {
            // InternalMyDsl.g:1698:1: ( ( rule__EquationTemplate__Group_5__0 )? )
            // InternalMyDsl.g:1699:2: ( rule__EquationTemplate__Group_5__0 )?
            {
             before(grammarAccess.getEquationTemplateAccess().getGroup_5()); 
            // InternalMyDsl.g:1700:2: ( rule__EquationTemplate__Group_5__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==28) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMyDsl.g:1700:3: rule__EquationTemplate__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__EquationTemplate__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEquationTemplateAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__5__Impl"


    // $ANTLR start "rule__EquationTemplate__Group__6"
    // InternalMyDsl.g:1708:1: rule__EquationTemplate__Group__6 : rule__EquationTemplate__Group__6__Impl ;
    public final void rule__EquationTemplate__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1712:1: ( rule__EquationTemplate__Group__6__Impl )
            // InternalMyDsl.g:1713:2: rule__EquationTemplate__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__6"


    // $ANTLR start "rule__EquationTemplate__Group__6__Impl"
    // InternalMyDsl.g:1719:1: rule__EquationTemplate__Group__6__Impl : ( '}' ) ;
    public final void rule__EquationTemplate__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1723:1: ( ( '}' ) )
            // InternalMyDsl.g:1724:1: ( '}' )
            {
            // InternalMyDsl.g:1724:1: ( '}' )
            // InternalMyDsl.g:1725:2: '}'
            {
             before(grammarAccess.getEquationTemplateAccess().getRightCurlyBracketKeyword_6()); 
            match(input,13,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group__6__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_3__0"
    // InternalMyDsl.g:1735:1: rule__EquationTemplate__Group_3__0 : rule__EquationTemplate__Group_3__0__Impl rule__EquationTemplate__Group_3__1 ;
    public final void rule__EquationTemplate__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1739:1: ( rule__EquationTemplate__Group_3__0__Impl rule__EquationTemplate__Group_3__1 )
            // InternalMyDsl.g:1740:2: rule__EquationTemplate__Group_3__0__Impl rule__EquationTemplate__Group_3__1
            {
            pushFollow(FOLLOW_15);
            rule__EquationTemplate__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_3__0"


    // $ANTLR start "rule__EquationTemplate__Group_3__0__Impl"
    // InternalMyDsl.g:1747:1: rule__EquationTemplate__Group_3__0__Impl : ( 'sourceParameters' ) ;
    public final void rule__EquationTemplate__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1751:1: ( ( 'sourceParameters' ) )
            // InternalMyDsl.g:1752:1: ( 'sourceParameters' )
            {
            // InternalMyDsl.g:1752:1: ( 'sourceParameters' )
            // InternalMyDsl.g:1753:2: 'sourceParameters'
            {
             before(grammarAccess.getEquationTemplateAccess().getSourceParametersKeyword_3_0()); 
            match(input,26,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getSourceParametersKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_3__0__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_3__1"
    // InternalMyDsl.g:1762:1: rule__EquationTemplate__Group_3__1 : rule__EquationTemplate__Group_3__1__Impl ;
    public final void rule__EquationTemplate__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1766:1: ( rule__EquationTemplate__Group_3__1__Impl )
            // InternalMyDsl.g:1767:2: rule__EquationTemplate__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_3__1"


    // $ANTLR start "rule__EquationTemplate__Group_3__1__Impl"
    // InternalMyDsl.g:1773:1: rule__EquationTemplate__Group_3__1__Impl : ( ( rule__EquationTemplate__SourceParametersAssignment_3_1 ) ) ;
    public final void rule__EquationTemplate__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1777:1: ( ( ( rule__EquationTemplate__SourceParametersAssignment_3_1 ) ) )
            // InternalMyDsl.g:1778:1: ( ( rule__EquationTemplate__SourceParametersAssignment_3_1 ) )
            {
            // InternalMyDsl.g:1778:1: ( ( rule__EquationTemplate__SourceParametersAssignment_3_1 ) )
            // InternalMyDsl.g:1779:2: ( rule__EquationTemplate__SourceParametersAssignment_3_1 )
            {
             before(grammarAccess.getEquationTemplateAccess().getSourceParametersAssignment_3_1()); 
            // InternalMyDsl.g:1780:2: ( rule__EquationTemplate__SourceParametersAssignment_3_1 )
            // InternalMyDsl.g:1780:3: rule__EquationTemplate__SourceParametersAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__SourceParametersAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getEquationTemplateAccess().getSourceParametersAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_3__1__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_4__0"
    // InternalMyDsl.g:1789:1: rule__EquationTemplate__Group_4__0 : rule__EquationTemplate__Group_4__0__Impl rule__EquationTemplate__Group_4__1 ;
    public final void rule__EquationTemplate__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1793:1: ( rule__EquationTemplate__Group_4__0__Impl rule__EquationTemplate__Group_4__1 )
            // InternalMyDsl.g:1794:2: rule__EquationTemplate__Group_4__0__Impl rule__EquationTemplate__Group_4__1
            {
            pushFollow(FOLLOW_15);
            rule__EquationTemplate__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_4__0"


    // $ANTLR start "rule__EquationTemplate__Group_4__0__Impl"
    // InternalMyDsl.g:1801:1: rule__EquationTemplate__Group_4__0__Impl : ( 'contactParameters' ) ;
    public final void rule__EquationTemplate__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1805:1: ( ( 'contactParameters' ) )
            // InternalMyDsl.g:1806:1: ( 'contactParameters' )
            {
            // InternalMyDsl.g:1806:1: ( 'contactParameters' )
            // InternalMyDsl.g:1807:2: 'contactParameters'
            {
             before(grammarAccess.getEquationTemplateAccess().getContactParametersKeyword_4_0()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getContactParametersKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_4__0__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_4__1"
    // InternalMyDsl.g:1816:1: rule__EquationTemplate__Group_4__1 : rule__EquationTemplate__Group_4__1__Impl ;
    public final void rule__EquationTemplate__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1820:1: ( rule__EquationTemplate__Group_4__1__Impl )
            // InternalMyDsl.g:1821:2: rule__EquationTemplate__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_4__1"


    // $ANTLR start "rule__EquationTemplate__Group_4__1__Impl"
    // InternalMyDsl.g:1827:1: rule__EquationTemplate__Group_4__1__Impl : ( ( rule__EquationTemplate__ContactParametersAssignment_4_1 ) ) ;
    public final void rule__EquationTemplate__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1831:1: ( ( ( rule__EquationTemplate__ContactParametersAssignment_4_1 ) ) )
            // InternalMyDsl.g:1832:1: ( ( rule__EquationTemplate__ContactParametersAssignment_4_1 ) )
            {
            // InternalMyDsl.g:1832:1: ( ( rule__EquationTemplate__ContactParametersAssignment_4_1 ) )
            // InternalMyDsl.g:1833:2: ( rule__EquationTemplate__ContactParametersAssignment_4_1 )
            {
             before(grammarAccess.getEquationTemplateAccess().getContactParametersAssignment_4_1()); 
            // InternalMyDsl.g:1834:2: ( rule__EquationTemplate__ContactParametersAssignment_4_1 )
            // InternalMyDsl.g:1834:3: rule__EquationTemplate__ContactParametersAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__ContactParametersAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getEquationTemplateAccess().getContactParametersAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_4__1__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_5__0"
    // InternalMyDsl.g:1843:1: rule__EquationTemplate__Group_5__0 : rule__EquationTemplate__Group_5__0__Impl rule__EquationTemplate__Group_5__1 ;
    public final void rule__EquationTemplate__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1847:1: ( rule__EquationTemplate__Group_5__0__Impl rule__EquationTemplate__Group_5__1 )
            // InternalMyDsl.g:1848:2: rule__EquationTemplate__Group_5__0__Impl rule__EquationTemplate__Group_5__1
            {
            pushFollow(FOLLOW_15);
            rule__EquationTemplate__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_5__0"


    // $ANTLR start "rule__EquationTemplate__Group_5__0__Impl"
    // InternalMyDsl.g:1855:1: rule__EquationTemplate__Group_5__0__Impl : ( 'contactCompartment' ) ;
    public final void rule__EquationTemplate__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1859:1: ( ( 'contactCompartment' ) )
            // InternalMyDsl.g:1860:1: ( 'contactCompartment' )
            {
            // InternalMyDsl.g:1860:1: ( 'contactCompartment' )
            // InternalMyDsl.g:1861:2: 'contactCompartment'
            {
             before(grammarAccess.getEquationTemplateAccess().getContactCompartmentKeyword_5_0()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getEquationTemplateAccess().getContactCompartmentKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_5__0__Impl"


    // $ANTLR start "rule__EquationTemplate__Group_5__1"
    // InternalMyDsl.g:1870:1: rule__EquationTemplate__Group_5__1 : rule__EquationTemplate__Group_5__1__Impl ;
    public final void rule__EquationTemplate__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1874:1: ( rule__EquationTemplate__Group_5__1__Impl )
            // InternalMyDsl.g:1875:2: rule__EquationTemplate__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_5__1"


    // $ANTLR start "rule__EquationTemplate__Group_5__1__Impl"
    // InternalMyDsl.g:1881:1: rule__EquationTemplate__Group_5__1__Impl : ( ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 ) ) ;
    public final void rule__EquationTemplate__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1885:1: ( ( ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 ) ) )
            // InternalMyDsl.g:1886:1: ( ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 ) )
            {
            // InternalMyDsl.g:1886:1: ( ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 ) )
            // InternalMyDsl.g:1887:2: ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 )
            {
             before(grammarAccess.getEquationTemplateAccess().getContactCompartmentAssignment_5_1()); 
            // InternalMyDsl.g:1888:2: ( rule__EquationTemplate__ContactCompartmentAssignment_5_1 )
            // InternalMyDsl.g:1888:3: rule__EquationTemplate__ContactCompartmentAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__EquationTemplate__ContactCompartmentAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getEquationTemplateAccess().getContactCompartmentAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__Group_5__1__Impl"


    // $ANTLR start "rule__PhysicalEpidemic__CompartmentsAssignment_3_2"
    // InternalMyDsl.g:1897:1: rule__PhysicalEpidemic__CompartmentsAssignment_3_2 : ( rulePhysicalCompartment ) ;
    public final void rule__PhysicalEpidemic__CompartmentsAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1901:1: ( ( rulePhysicalCompartment ) )
            // InternalMyDsl.g:1902:2: ( rulePhysicalCompartment )
            {
            // InternalMyDsl.g:1902:2: ( rulePhysicalCompartment )
            // InternalMyDsl.g:1903:3: rulePhysicalCompartment
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_2_0()); 
            pushFollow(FOLLOW_2);
            rulePhysicalCompartment();

            state._fsp--;

             after(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__CompartmentsAssignment_3_2"


    // $ANTLR start "rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1"
    // InternalMyDsl.g:1912:1: rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1 : ( rulePhysicalCompartment ) ;
    public final void rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1916:1: ( ( rulePhysicalCompartment ) )
            // InternalMyDsl.g:1917:2: ( rulePhysicalCompartment )
            {
            // InternalMyDsl.g:1917:2: ( rulePhysicalCompartment )
            // InternalMyDsl.g:1918:3: rulePhysicalCompartment
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_3_1_0()); 
            pushFollow(FOLLOW_2);
            rulePhysicalCompartment();

            state._fsp--;

             after(grammarAccess.getPhysicalEpidemicAccess().getCompartmentsPhysicalCompartmentParserRuleCall_3_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__CompartmentsAssignment_3_3_1"


    // $ANTLR start "rule__PhysicalEpidemic__FlowsAssignment_4_2"
    // InternalMyDsl.g:1927:1: rule__PhysicalEpidemic__FlowsAssignment_4_2 : ( rulePhysicalFlow ) ;
    public final void rule__PhysicalEpidemic__FlowsAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1931:1: ( ( rulePhysicalFlow ) )
            // InternalMyDsl.g:1932:2: ( rulePhysicalFlow )
            {
            // InternalMyDsl.g:1932:2: ( rulePhysicalFlow )
            // InternalMyDsl.g:1933:3: rulePhysicalFlow
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_2_0()); 
            pushFollow(FOLLOW_2);
            rulePhysicalFlow();

            state._fsp--;

             after(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__FlowsAssignment_4_2"


    // $ANTLR start "rule__PhysicalEpidemic__FlowsAssignment_4_3_1"
    // InternalMyDsl.g:1942:1: rule__PhysicalEpidemic__FlowsAssignment_4_3_1 : ( rulePhysicalFlow ) ;
    public final void rule__PhysicalEpidemic__FlowsAssignment_4_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1946:1: ( ( rulePhysicalFlow ) )
            // InternalMyDsl.g:1947:2: ( rulePhysicalFlow )
            {
            // InternalMyDsl.g:1947:2: ( rulePhysicalFlow )
            // InternalMyDsl.g:1948:3: rulePhysicalFlow
            {
             before(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_3_1_0()); 
            pushFollow(FOLLOW_2);
            rulePhysicalFlow();

            state._fsp--;

             after(grammarAccess.getPhysicalEpidemicAccess().getFlowsPhysicalFlowParserRuleCall_4_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalEpidemic__FlowsAssignment_4_3_1"


    // $ANTLR start "rule__PhysicalCompartment__LabelsAssignment_3_2"
    // InternalMyDsl.g:1957:1: rule__PhysicalCompartment__LabelsAssignment_3_2 : ( ruleLabel ) ;
    public final void rule__PhysicalCompartment__LabelsAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1961:1: ( ( ruleLabel ) )
            // InternalMyDsl.g:1962:2: ( ruleLabel )
            {
            // InternalMyDsl.g:1962:2: ( ruleLabel )
            // InternalMyDsl.g:1963:3: ruleLabel
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_2_0()); 
            pushFollow(FOLLOW_2);
            ruleLabel();

            state._fsp--;

             after(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__LabelsAssignment_3_2"


    // $ANTLR start "rule__PhysicalCompartment__LabelsAssignment_3_3_1"
    // InternalMyDsl.g:1972:1: rule__PhysicalCompartment__LabelsAssignment_3_3_1 : ( ruleLabel ) ;
    public final void rule__PhysicalCompartment__LabelsAssignment_3_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1976:1: ( ( ruleLabel ) )
            // InternalMyDsl.g:1977:2: ( ruleLabel )
            {
            // InternalMyDsl.g:1977:2: ( ruleLabel )
            // InternalMyDsl.g:1978:3: ruleLabel
            {
             before(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleLabel();

            state._fsp--;

             after(grammarAccess.getPhysicalCompartmentAccess().getLabelsLabelParserRuleCall_3_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalCompartment__LabelsAssignment_3_3_1"


    // $ANTLR start "rule__PhysicalFlow__IdAssignment_2_1"
    // InternalMyDsl.g:1987:1: rule__PhysicalFlow__IdAssignment_2_1 : ( ruleEString ) ;
    public final void rule__PhysicalFlow__IdAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:1991:1: ( ( ruleEString ) )
            // InternalMyDsl.g:1992:2: ( ruleEString )
            {
            // InternalMyDsl.g:1992:2: ( ruleEString )
            // InternalMyDsl.g:1993:3: ruleEString
            {
             before(grammarAccess.getPhysicalFlowAccess().getIdEStringParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getPhysicalFlowAccess().getIdEStringParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__IdAssignment_2_1"


    // $ANTLR start "rule__PhysicalFlow__FromAssignment_3_1"
    // InternalMyDsl.g:2002:1: rule__PhysicalFlow__FromAssignment_3_1 : ( ( ruleEString ) ) ;
    public final void rule__PhysicalFlow__FromAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2006:1: ( ( ( ruleEString ) ) )
            // InternalMyDsl.g:2007:2: ( ( ruleEString ) )
            {
            // InternalMyDsl.g:2007:2: ( ( ruleEString ) )
            // InternalMyDsl.g:2008:3: ( ruleEString )
            {
             before(grammarAccess.getPhysicalFlowAccess().getFromPhysicalCompartmentCrossReference_3_1_0()); 
            // InternalMyDsl.g:2009:3: ( ruleEString )
            // InternalMyDsl.g:2010:4: ruleEString
            {
             before(grammarAccess.getPhysicalFlowAccess().getFromPhysicalCompartmentEStringParserRuleCall_3_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getPhysicalFlowAccess().getFromPhysicalCompartmentEStringParserRuleCall_3_1_0_1()); 

            }

             after(grammarAccess.getPhysicalFlowAccess().getFromPhysicalCompartmentCrossReference_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__FromAssignment_3_1"


    // $ANTLR start "rule__PhysicalFlow__ToAssignment_4_1"
    // InternalMyDsl.g:2021:1: rule__PhysicalFlow__ToAssignment_4_1 : ( ( ruleEString ) ) ;
    public final void rule__PhysicalFlow__ToAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2025:1: ( ( ( ruleEString ) ) )
            // InternalMyDsl.g:2026:2: ( ( ruleEString ) )
            {
            // InternalMyDsl.g:2026:2: ( ( ruleEString ) )
            // InternalMyDsl.g:2027:3: ( ruleEString )
            {
             before(grammarAccess.getPhysicalFlowAccess().getToPhysicalCompartmentCrossReference_4_1_0()); 
            // InternalMyDsl.g:2028:3: ( ruleEString )
            // InternalMyDsl.g:2029:4: ruleEString
            {
             before(grammarAccess.getPhysicalFlowAccess().getToPhysicalCompartmentEStringParserRuleCall_4_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getPhysicalFlowAccess().getToPhysicalCompartmentEStringParserRuleCall_4_1_0_1()); 

            }

             after(grammarAccess.getPhysicalFlowAccess().getToPhysicalCompartmentCrossReference_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__ToAssignment_4_1"


    // $ANTLR start "rule__PhysicalFlow__EquationtemplateAssignment_6"
    // InternalMyDsl.g:2040:1: rule__PhysicalFlow__EquationtemplateAssignment_6 : ( ruleEquationTemplate ) ;
    public final void rule__PhysicalFlow__EquationtemplateAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2044:1: ( ( ruleEquationTemplate ) )
            // InternalMyDsl.g:2045:2: ( ruleEquationTemplate )
            {
            // InternalMyDsl.g:2045:2: ( ruleEquationTemplate )
            // InternalMyDsl.g:2046:3: ruleEquationTemplate
            {
             before(grammarAccess.getPhysicalFlowAccess().getEquationtemplateEquationTemplateParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleEquationTemplate();

            state._fsp--;

             after(grammarAccess.getPhysicalFlowAccess().getEquationtemplateEquationTemplateParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhysicalFlow__EquationtemplateAssignment_6"


    // $ANTLR start "rule__Label__NameAssignment_2"
    // InternalMyDsl.g:2055:1: rule__Label__NameAssignment_2 : ( ruleEString ) ;
    public final void rule__Label__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2059:1: ( ( ruleEString ) )
            // InternalMyDsl.g:2060:2: ( ruleEString )
            {
            // InternalMyDsl.g:2060:2: ( ruleEString )
            // InternalMyDsl.g:2061:3: ruleEString
            {
             before(grammarAccess.getLabelAccess().getNameEStringParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getLabelAccess().getNameEStringParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Label__NameAssignment_2"


    // $ANTLR start "rule__EquationTemplate__SourceParametersAssignment_3_1"
    // InternalMyDsl.g:2070:1: rule__EquationTemplate__SourceParametersAssignment_3_1 : ( ruleEString ) ;
    public final void rule__EquationTemplate__SourceParametersAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2074:1: ( ( ruleEString ) )
            // InternalMyDsl.g:2075:2: ( ruleEString )
            {
            // InternalMyDsl.g:2075:2: ( ruleEString )
            // InternalMyDsl.g:2076:3: ruleEString
            {
             before(grammarAccess.getEquationTemplateAccess().getSourceParametersEStringParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getEquationTemplateAccess().getSourceParametersEStringParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__SourceParametersAssignment_3_1"


    // $ANTLR start "rule__EquationTemplate__ContactParametersAssignment_4_1"
    // InternalMyDsl.g:2085:1: rule__EquationTemplate__ContactParametersAssignment_4_1 : ( ruleEString ) ;
    public final void rule__EquationTemplate__ContactParametersAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2089:1: ( ( ruleEString ) )
            // InternalMyDsl.g:2090:2: ( ruleEString )
            {
            // InternalMyDsl.g:2090:2: ( ruleEString )
            // InternalMyDsl.g:2091:3: ruleEString
            {
             before(grammarAccess.getEquationTemplateAccess().getContactParametersEStringParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getEquationTemplateAccess().getContactParametersEStringParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__ContactParametersAssignment_4_1"


    // $ANTLR start "rule__EquationTemplate__ContactCompartmentAssignment_5_1"
    // InternalMyDsl.g:2100:1: rule__EquationTemplate__ContactCompartmentAssignment_5_1 : ( ruleEString ) ;
    public final void rule__EquationTemplate__ContactCompartmentAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMyDsl.g:2104:1: ( ( ruleEString ) )
            // InternalMyDsl.g:2105:2: ( ruleEString )
            {
            // InternalMyDsl.g:2105:2: ( ruleEString )
            // InternalMyDsl.g:2106:3: ruleEString
            {
             before(grammarAccess.getEquationTemplateAccess().getContactCompartmentEStringParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEString();

            state._fsp--;

             after(grammarAccess.getEquationTemplateAccess().getContactCompartmentEStringParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EquationTemplate__ContactCompartmentAssignment_5_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000016000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000000A000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000042000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000F00000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x000000001C002000L});

}