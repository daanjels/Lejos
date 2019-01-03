package drawing;

	//******************************************************************
	// Play a "song" consisting of predefined beeps on a thread
	//******************************************************************

import lejos.hardware.Sound;
import utility.Delay;
	public class Songs extends Thread {
		public void run() {
			short C4=262, D4=294, E4=330, F4=349, F4_sharp=370, G4=392, A4=440, B4=494, C5=523, D5=587, E5=659, rest=0;
			short[] wish = 
				{
					D4, 250, 
					// we
					G4, 250, G4, 125, A4, 125, G4, 125, F4_sharp, 125, 
					// wish  you      a        mer      -ry
					E4, 250, C4, 250, E4, 250, 
					// Christ -mas,   we
					A4, 250, A4, 125, B4, 125, A4, 125, G4, 125, 
					//  wish you      a        Mer      -ry
					F4_sharp, 250, D4, 250, D4, 250, 
					// Christ      -mas,    we
					B4, 250, B4, 125, C5, 125, B4, 125, A4, 125, 
					// wish  you      a        Mer      -ry
					G4, 250, E4, 250, D4, 125, D4, 125, 
					// Christ -mas    and      a
					E4, 250, A4, 250, F4_sharp, 250, 
					// hap   -py      New
					G4, 500, D4, 250, 
					// Year! Glad
					G4, 250, G4, 250, G4, 250, 
					// tid   -ings    we
					F4_sharp, 500, F4_sharp, 250, 
					// bring       to
					G4, 250, F4_sharp, 250, E4, 250, 
					// you   and            your
					D4, 500, A4, 250, 
					// kin.  Glad
					B4, 250, A4, 250, G4, 250, 
					// tid   -ings    for
					D5, 250, D4, 250, D4, 125, D4, 125, 
					// Christ -mas    and      a
					E4, 250, A4, 250, F4_sharp, 250, 
					// Hap   -py      New
					G4, 500, D4, 250, 
					// Year! We
					G4, 250, G4, 125, A4, 125, G4, 125, F4_sharp, 125, 
					//wish   you      a mer    -ry
					E4, 250, C4, 250, E4, 250, 
					// Christ -mas    we
					A4, 250, A4, 125, B4, 125, A4, 125, A4, 125, 
					// wish  you      a        mer      -ry
					F4_sharp, 250, D4, 250, D4, 250, 
					// Christ      -mas,    we
					B4, 250, B4, 125, C5, 125, B4, 125, A4, 125, 
					// wish  you      a        Mer      -ry
					G4, 250, E4, 250, D4, 125, D4, 125, 
					// Christ -mas    and      a
					E4, 250, A4, 250, F4_sharp, 250, 
					// hap   -py      New
					G4, 500, D4, 250, 
					// Year! Glad
					G4, 250, G4, 250, G4, 250, 
					// tid   -ings    we
					F4_sharp, 500, F4_sharp, 250, 
					// bring       to
					G4, 250, F4_sharp, 250, E4, 250, 
					// you   and            your
					D4, 500, A4, 250, 
					// kin.  Glad
					B4, 250, A4, 250, G4, 250, 
					// tid   -ings    for
					D5, 250, D4, 250, D4, 125, D4, 125, 
					// Christ -mas    and      a
					E4, 250, A4, 250, F4_sharp, 250, 
					// Hap   -py      New
					G4, 500, D4, 250, 
					// Year! We
					G4, 250, G4, 125, A4, 125, G4, 125, F4_sharp, 125, 
					//wish   you      a mer    -ry
					E4, 250, C4, 250, E4, 250, 
					// Christ -mas    we
					A4, 250, A4, 125, B4, 125, A4, 125, A4, 125, 
					// wish  you      a        mer      -ry
					F4_sharp, 250, D4, 250, D4, 250, 
					// Christ      -mas,    we
					B4, 250, B4, 125, C5, 125, B4, 125, A4, 125, 
					// wish  you      a        Mer      -ry
					G4, 250, E4, 250, D4, 125, D4, 125, 
					// Christ -mas    and      a
					E4, 250, A4, 250, F4_sharp, 250, 
					// hap   -py      New
					G4, 750 
					// Year!
				};
			short[] jingle =
				{
					D4, 160, B4, 160, A4, 160, G4, 160, 
					// Dash  -ing     through  the
					D4, 480, D4, 80, D4, 80, 
					// snow.  In       a
					D4, 160, B4, 160, A4, 160, G4, 160,
					// one   -horse   o-       pen
					E4, 480, rest, 160,
					// sleigh
					E4, 160, C5, 160, B4, 160, A4, 160,
					// Over  the      fields   we
					F4_sharp, 480, rest, 160,
					// go,
					D5, 160, D5, 160, C5, 160, A4, 160,
					// Laugh -ing     all      the
					G4, 800, 
					// way.
					D4, 160, B4, 160, A4, 160, G4, 160, 
					// Bells on       bob      -tal
					D4, 480, rest, 160,
					// ring.
					D4, 160, B4, 160, A4, 160, G4, 160,
					// Ma    -king    spi-     rits
					E4, 480, E4, 160,
					// bright. What
					E4, 160, C5, 160, B4, 160, A4, 160,
					// fun   it       is       to
					D5, 160, D5, 160, D5, 160, D5, 160,
					// ride  and      sing     a
					E5, 160, D5, 160, C5, 160, A4, 160,
					// sleigh -ing    song     to
					G4, 480, rest, 160,
					// night!

					// refrain
					G4, 160, G4, 160, G4, 320,
					// Jin-  gle      bells,
					G4, 160, G4, 160, G4, 320,
					// jin-  gle      bells
					G4, 160, D5, 160, G4, 240, A4, 80,
					// Jin-  gle      all      the
					B4, 480, rest, 160,
					// way!
					C5, 160, C5, 160, C5, 240, C5, 80,
					// Oh    what     fun      it
					C5, 160, B4, 160, B4, 160, B4, 80, B4, 80,
					// is    to       ride     in       a
					B4, 160, A4, 160, A4, 160, B4, 160,
					// one   -horse   o-       pen
					A4, 320, D5, 320,
					// sleigh!
					G4, 160, G4, 160, G4, 320,
					// Jin-  gle      bells,
					G4, 160, G4, 160, G4, 320,
					// jin-  gle      bells
					G4, 160, D5, 160, G4, 240, A4, 80,
					// Jin-  gle      all      the
					B4, 480, rest, 160,
					// way!
					C5, 160, C5, 160, C5, 240, C5, 80,
					// Oh    what     fun      it
					C5, 160, B4, 160, B4, 160, B4, 80, B4, 80,
					// is    to       ride     in       a
					D5, 160, D5, 160, C5, 160, A4, 160,
					// one   -horse   o-       pen
					G4, 480, rest, 160,
					// sleigh!
					
					D4, 160, B4, 160, A4, 160, G4, 160, 
					// A     day      or       two
					D4, 480, D4, 80, D4, 80, 
					// ago.           The      
					D4, 160, B4, 160, A4, 160, G4, 160,
					// sto-  ry       I        must
					E4, 480, rest, 160,
					// tell
					E4, 160, C5, 160, B4, 160, A4, 160,
					// I went out     on       the
					F4_sharp, 480, rest, 160,
					// snow,
					D5, 160, D5, 160, C5, 160, A4, 160,
					// And on my      back     I
					G4, 800, 
					// fell.
					D4, 160, B4, 160, A4, 160, G4, 160, 
					// A gent was     ri-      ding
					D4, 480, rest, 160,
					// by.
					D4, 160, B4, 160, A4, 160, G4, 160,
					// In a  one-     horse    open
					E4, 480, E4, 160,
					// sleigh. He
					E4, 160, C5, 160, B4, 160, A4, 160,
					// laughed at     me       as
					D5, 160, D5, 160, D5, 160, D5, 160,
					// I     there    sprawling laid
					E5, 160, D5, 160, C5, 160, A4, 160,
					// but   quickly  drove    a
					G4, 480, rest, 160,
					// -way
					G4, 160, G4, 160, G4, 320,
					// Jin-  gle      bells,
					G4, 160, G4, 160, G4, 320,
					// jin-  gle      bells
					G4, 160, D5, 160, G4, 240, A4, 80,
					// Jin-  gle      all      the
					B4, 480, rest, 160,
					// way!
					C5, 160, C5, 160, C5, 240, C5, 80,
					// Oh    what     fun      it
					C5, 160, B4, 160, B4, 160, B4, 80, B4, 80,
					// is    to       ride     in       a
					D5, 160, D5, 160, C5, 160, A4, 160,
					// one   -horse   o-       pen
					G4, 480, rest, 160,
					// sleigh!
				};
			Sound.setVolume(50);
			for (int i = 0; i < wish.length; i += 2) {
				short w = wish[i + 1];
					Sound.playTone(wish[i], w);
					Sound.pause(w);
			}
			Delay.msDelay(2000);
			for (int i = 0; i < jingle.length; i += 2) {
				short w = jingle[i + 1];
				if (jingle[i] != rest) {
					Sound.playTone(jingle[i], w);
					Sound.pause(w);
				} else {
					Delay.msDelay(w);
				}
			}		
		}
	}