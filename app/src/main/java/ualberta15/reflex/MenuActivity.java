/*Licensed to the Apache Software Foundation (ASF) under one
        or more contributor license agreements.  See the NOTICE file
        distributed with this work for additional information
        regarding copyright ownership.  The ASF licenses this file
        to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
        with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,
        software distributed under the License is distributed on an
        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        KIND, either express or implied.  See the License for the
        specific language governing permissions and limitations
        under the License.
*/

package ualberta15.reflex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Activity for the Main Menu of the Application
public class MenuActivity extends AppCompatActivity {
    //Generates a Input Output Manager to be passed throughout activities
    protected IOManager appIOMan = new IOManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Generate the buttons on the menu, and sets their Click Listeners to open different activities
        Button singlePlayerModeButton = (Button) findViewById(R.id.modeButtonSinglePlayer);
        singlePlayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 1);
            }
        });

        Button multiPlayerModeButton = (Button) findViewById(R.id.modeButtonMultiPlayer);
        multiPlayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 2);
            }
        });

        Button statisticButton = (Button) findViewById(R.id.modeButtonStatistics);
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(v, 3);
            }
        });
    }

    //Opens different activities depending on the inputted mode, as well as passing the IOManager to them
    public void changeMode(View view, int mode){
        Intent intent = null;
        if (mode == 1) {
            intent = new Intent(this, SinglePlayerActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode == 2){
            intent = new Intent(this, MultiplayerMenuActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        if (mode ==3){
            intent = new Intent(this, StatisticsActivity.class);
            intent.putExtra("IOManager", appIOMan);
        }
        startActivity(intent);
    }

}
