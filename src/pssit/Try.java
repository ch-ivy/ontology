/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pssit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class Try {
     //ALLERGY.owl physical address on your computer
    public static final File Phy_Add_IRI = new File("ONTOLOGY/PSSITontology.owl");
    //ALLERGY.owl base address in your ontology file
    public static final IRI Onto_Base_IRI = IRI.create("http://securityontology/PSSIT");

    OWLOntologyManager m = OWLManager.createOWLOntologyManager();
    //Create an Ontology Factory object f 
    OWLDataFactory f = OWLManager.getOWLDataFactory();
    //Create an Ontology object o
    OWLOntology o = null;
 public List getAnObjectProperty(String ind, String OTP) {
        List<String> list = new ArrayList<String>();

        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            PelletReasoner r = PelletReasonerFactory.getInstance().createReasoner(o);
            OWLNamedIndividual individual = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + ind));
            OWLObjectProperty op = f.getOWLObjectProperty(IRI.create(Onto_Base_IRI + "#" + OTP));
            NodeSet<OWLNamedIndividual> value = r.getObjectPropertyValues(individual, op);

            for (OWLNamedIndividual rangeVal : value.getFlattened()) {
                System.out.println(rangeVal.toString());
            }

            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }

        return list;
    }

    public List getADataProperty(String indv, String DTP) {
        List<String> list = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            PelletReasoner r = PelletReasonerFactory.getInstance().createReasoner(o);
            OWLNamedIndividual ind = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + indv));
            OWLDataProperty dp = f.getOWLDataProperty(IRI.create(Onto_Base_IRI + "#" + DTP));
            Set<OWLLiteral> aset = r.getDataPropertyValues(ind, dp);
            for (OWLLiteral val : aset) {
                System.out.println(val.getLiteral().toString());
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return list;
    }

    public static void main(String[] args) {
        Try obj = new Try();
        obj.getAnObjectProperty("Peter_Frank","hasID");
    }}

