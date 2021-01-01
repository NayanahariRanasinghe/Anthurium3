package com.example.anthurium3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detector extends AppCompatActivity {

    TextView disease;
    TextView symptoms;
    TextView treatment;
    int result = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        disease = findViewById(R.id.txtDisease);
        symptoms = findViewById(R.id.txtSymptoms);
        treatment = findViewById(R.id.txtTreatments);

        this.result = getIntent().getExtras().getInt("result");
        Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();

        predictDisease(result);

        disease.setMovementMethod(new ScrollingMovementMethod());
        symptoms.setMovementMethod(new ScrollingMovementMethod());
        treatment.setMovementMethod(new ScrollingMovementMethod());
    }

    private void predictDisease(int i) {
        switch (i){
            case 1:
                disease.setText("Bacterial Blight");
                symptoms.setText("1.\tyellowed (chlorotic), \n" +
                        "2.\twater-soaked lesions along the leaf margins that grow rapidly to form dead (necrotic) V-shaped lesions\n");
                treatment.setText("1.\tLower greenhouse humidity and temperature by increasing air circulation and venting the production facility. Allow space between the plants on the bench. Warm temperatures, high humidity, and saturated soils contribute to the formation of guttation droplets.\n" +
                        "2.\tOnly clean, tissue-cultured plantlets should be used when establishing new plantings. Because Xanthomonas bacteria can enter the plant through any wound or tear in the stem or foliage, the disease is easily spread if propagation is done via cuttings or division.\n" +
                        "3.\tThe disease can be spread when harvesting flowers or removing old foliage. Sterilize knives and clippers by dipping cutting tools in a disinfectant between plants. \n" +
                        "4.\tThe most effective disinfectants are the quaternary ammonium compounds. \n" +
                        "5.\tIt is also good practice to have two knives or shears in a dip bucket so they can be alternated, thus extending time in disinfectant and allowing for better coverage of the cutting surface.\n" +
                        "6.\tWhen infected plants are found, they should be discarded immediately.\n" +
                        "7.\tProducts containing copper (Kocide® 3000 O, Phyton 27®, Camelot), mancozeb (Protect T/O™, Dithane®), and Bacillus subtilis (Cease®) are effective against Xanthomonas.\n");

                this.result = -1;
                break;
            case 2:
                disease.setText("Rhizoctonia Root Rot");
                symptoms.setText("1.\tAnthurium wilt caused by Rhizoctonia. \n" +
                        "\n" +
                        "2.\tDiscolored brown roots are one of the. \n" +
                        "\n" +
                        "3.\tWilt symptoms are the byproduct of Ralstonia bacteria clogging the vascular \n");
                treatment.setText("1.\tNever incorporate native soils into media mixes without steam sterilizing. Use well-drained soil mixes. \n" +
                        "\n" +
                        "2.\tNever store peat moss, sphagnum moss, chips, or potting media mixes directly on soil surfaces where they can be colonized by the fungus. \n" +
                        "\n" +
                        "3.\tPlants should be cultivated on raised benches to limit root contact with soil. \n" +
                        "\n" +
                        "4.\tRhizoctonia frequently gains access to production facilities via infected propagation material. \n" +
                        "\n" +
                        "5.\tIdentification and Control in Commercial Greenhouse Operations 5 Many fungicides are effective against outbreaks of Rhizoctonia. Examples include 3336®, OHP 6672® (thiophanate methyl), Medallion® (fludioxonil), and Prostar® (flutolanil).\n");
                this.result = -1;
                break;
            case 3:
                disease.setText("Black Nose Disease");
                symptoms.setText("1.\tBlack nose can cause havoc in cut-flower and potted-plant production. Flowers and flowering potted plants cannot be sold with this condition.\n" +
                        "\n" +
                        "2.\tThe first symptoms observed are small, brown to black flecks on the floral spadix (nose) . These spots rapidly enlarge, become watery, turn brown to black, and may totally encompass the spadix. The spadix may eventually fall off. \n" +
                        "\n" +
                        "\n" +
                        "3.\tGrowers may also observe black, sporecontaining structures (acervuli) on dead leaves and stems\n");
                treatment.setText("1.\tThe Colletotrichum fungus produces thousands of small hot-dog-shaped spores that can readily be moved by splashing water, air movement, and workers.\n" +
                        "\n" +
                        "2.\tA strict sanitation program is crucial to control the spread of this pathogen in a production facility. \n" +
                        "\n" +
                        "3.\tFungicides containing mancozeb (Protect T/O™, Dithane®) are effective. \n" +
                        "\n" +
                        "4.\tFungicide applications are usually discouraged because chemical residues diminish the marketability of flowers and plants. \n" +
                        "\n" +
                        "5.\tAnthurium plant breeding programs both in Hawaii and Florida have incorporated disease resistance into many of the current cultivars. Newer cultivars are highly resistant to this pathogen and rarely exhibit black nose.\n");
                this.result = -1;

            default:
                break;
        }
    }
}
