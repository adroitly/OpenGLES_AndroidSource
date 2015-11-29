const float mHigh[64][64]=
{
95,99,139,177,206,221,222,227,229,216,204,190,160,127,104,86,76,83,90,97,113,136,159,175,181,155,133,123,108,95,88,82,77,77,82,84,87,93,100,109,121,114,101,97,93,84,75,72,72,69,68,67,68,70,66,62,60,63,64,67,73,73,67,90,
65,67,106,154,195,222,204,205,228,226,223,217,150,100,77,62,50,53,60,63,80,110,134,175,219,179,134,115,102,91,82,68,59,60,63,64,65,70,79,89,89,65,45,48,47,43,36,37,44,44,43,42,49,67,70,66,66,65,53,47,51,52,46,64,
54,49,84,128,194,238,188,163,212,217,214,206,125,70,59,46,37,40,51,53,66,95,118,175,232,224,203,185,146,104,90,74,64,64,63,62,61,67,79,88,72,42,28,27,26,22,22,27,31,33,35,42,94,160,175,179,177,170,146,94,55,46,40,56,
46,32,54,100,188,237,155,73,94,106,112,105,66,37,32,28,23,28,38,47,57,79,99,143,202,225,242,242,214,132,93,81,69,66,61,60,59,62,71,80,70,47,30,18,13,9,11,16,20,21,25,42,129,209,223,231,229,227,233,192,88,33,25,47,
38,20,36,88,197,234,128,20,17,26,33,38,32,22,17,16,16,22,28,40,47,53,72,94,127,162,202,233,224,138,86,79,75,67,62,60,61,64,68,69,65,51,31,15,8,7,10,11,12,15,14,18,50,97,144,218,221,185,207,230,131,33,16,38,
34,10,18,51,122,141,54,7,7,10,11,15,15,11,10,12,12,15,20,27,33,34,46,62,77,100,126,155,146,96,71,74,79,69,63,67,71,72,72,63,52,40,22,12,8,8,9,8,8,8,7,8,7,8,40,139,214,202,193,236,163,45,15,38,
30,9,9,16,28,23,9,3,2,2,2,3,5,5,6,7,9,9,10,16,21,23,29,40,56,71,77,83,81,66,65,76,80,73,69,73,75,71,62,51,37,24,12,8,8,8,7,6,4,3,4,4,4,5,10,53,160,229,233,239,177,41,11,35,
28,4,5,6,3,1,1,1,1,1,1,1,2,4,4,5,7,7,7,10,11,15,24,29,38,45,49,55,60,59,68,79,80,77,81,80,71,61,46,35,27,18,13,11,9,5,3,3,2,3,3,2,3,6,7,13,55,142,222,241,159,20,4,30,
27,2,3,2,1,1,1,2,2,2,1,1,2,3,4,6,7,7,7,8,13,21,31,36,37,36,43,54,63,66,72,74,76,76,80,77,67,52,36,28,23,20,17,13,9,4,1,1,2,2,1,1,3,6,7,9,10,45,179,239,149,17,3,31,
28,5,7,4,1,1,1,2,2,2,1,1,3,4,5,8,9,9,10,13,22,32,47,55,56,51,56,74,86,88,83,73,66,59,62,66,60,43,31,26,21,21,20,15,8,5,2,2,1,1,1,2,2,4,6,10,11,20,159,241,198,51,3,31,
30,7,8,4,3,2,1,1,2,2,1,2,2,4,5,8,13,13,16,26,35,51,66,70,74,79,83,93,105,102,90,79,66,53,49,55,53,41,30,25,23,22,17,13,8,4,2,0,0,0,1,3,3,3,5,11,10,13,160,241,208,59,3,31,
31,10,10,8,9,6,3,2,1,0,0,1,3,4,7,12,14,15,25,42,57,72,87,91,97,109,113,118,119,107,88,76,82,121,141,118,67,43,36,29,27,24,17,13,8,4,2,0,0,0,1,2,5,6,7,12,12,14,127,236,154,30,5,33,
34,13,17,21,15,7,4,2,1,0,0,0,2,4,8,14,22,27,36,60,79,91,103,113,122,124,123,124,120,103,86,85,135,223,239,223,134,80,87,50,31,28,23,18,10,4,1,0,0,1,4,6,7,9,8,12,16,17,45,100,49,9,8,35,
42,24,27,29,22,8,3,2,3,2,1,0,0,0,3,9,24,37,59,84,99,102,115,145,144,126,115,118,111,91,76,96,179,236,209,225,198,181,200,94,38,34,29,18,9,3,1,0,2,5,10,12,12,10,10,15,22,26,23,21,15,11,12,42,
56,34,34,36,31,16,5,3,5,7,6,4,1,0,0,2,8,26,69,100,111,114,154,213,195,135,108,111,101,81,68,96,190,233,199,202,212,225,207,102,46,42,33,16,5,2,2,2,9,14,21,25,25,18,14,18,25,28,27,22,21,21,21,51,
70,49,47,44,37,24,14,10,9,12,12,13,9,1,0,1,2,3,43,96,112,120,181,237,228,155,106,100,88,75,67,87,180,236,237,234,221,198,139,69,53,47,27,6,2,4,2,15,32,40,45,45,43,35,28,25,28,30,28,26,30,35,35,65,
81,60,54,45,38,31,21,14,14,15,16,22,25,14,2,0,2,5,15,64,89,96,148,211,195,138,99,84,71,66,66,67,95,138,147,143,126,96,65,56,59,42,11,5,8,5,16,47,64,68,73,68,63,62,60,52,46,43,41,42,47,51,48,74,
83,57,47,36,33,35,28,18,18,20,20,25,35,42,24,3,1,3,1,24,41,49,80,121,123,106,89,69,55,54,59,55,51,50,48,46,45,41,43,53,51,22,3,9,6,10,40,74,90,93,104,101,94,101,101,85,73,73,73,73,71,60,46,75,
77,50,38,30,28,32,31,26,21,24,26,27,40,57,62,29,2,0,1,3,8,22,41,64,80,81,69,53,41,41,47,45,41,42,40,35,35,35,41,43,23,5,8,8,5,26,73,104,118,126,132,138,140,142,136,118,100,101,107,106,92,70,54,76,
73,46,38,29,25,26,28,27,28,32,33,32,43,59,69,67,24,1,0,0,1,13,25,35,47,47,41,31,23,21,25,25,25,26,25,23,24,27,28,20,5,7,11,2,16,64,119,142,152,158,164,170,172,172,167,153,138,131,131,130,112,92,77,82,
73,48,41,38,65,65,38,32,36,36,36,38,45,62,79,93,69,15,0,0,1,7,11,11,14,14,11,9,6,5,5,6,6,6,6,6,6,7,8,3,2,7,7,16,57,109,148,169,181,188,192,195,201,199,191,183,170,161,151,146,135,116,95,94,
71,47,42,77,175,166,71,37,36,33,35,45,57,82,107,119,99,47,9,1,0,1,3,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,1,2,13,62,108,139,163,182,195,210,217,223,230,226,218,213,203,194,179,164,146,123,97,93,
62,43,42,112,220,187,89,40,34,34,43,60,85,107,129,140,123,83,40,8,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,44,107,140,161,182,199,212,228,237,245,252,247,238,232,227,220,200,174,147,115,83,81,
54,35,37,120,223,204,162,69,36,47,65,87,121,153,170,161,136,101,64,29,7,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,73,122,149,173,195,213,228,241,246,248,252,250,246,243,236,225,204,179,136,93,62,63,
50,29,32,121,225,222,229,123,35,51,72,114,184,219,226,190,135,92,65,44,25,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,27,76,113,139,163,185,206,230,242,244,244,244,244,244,246,244,226,205,184,141,95,59,57,
50,27,29,118,223,213,233,139,32,45,73,161,232,240,237,191,122,76,51,48,41,15,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,26,65,91,110,130,155,191,219,226,228,231,234,238,242,244,247,232,209,188,159,125,79,66,
51,27,31,119,224,212,232,140,35,44,84,202,239,234,213,146,94,66,55,59,48,27,6,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,23,54,70,86,104,131,179,204,201,186,180,186,206,234,244,249,245,223,198,172,141,93,77,
60,33,36,127,228,227,231,131,34,34,61,152,219,231,191,108,79,69,73,69,53,29,9,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,22,48,60,72,94,125,163,182,177,159,141,145,185,231,244,249,255,237,207,174,139,95,82,
65,36,37,138,236,238,216,99,32,30,37,65,111,134,106,74,72,80,80,69,50,26,7,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,22,45,53,66,90,118,135,150,146,128,115,133,183,231,245,247,249,230,202,166,120,85,87,
70,40,36,123,229,214,131,49,31,28,31,38,47,55,59,60,67,76,73,59,38,20,5,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,23,45,52,68,91,108,108,115,108,103,111,143,184,229,246,245,239,206,184,151,103,72,86,
74,43,33,65,134,107,46,33,30,22,24,27,32,40,44,48,51,53,47,34,25,14,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,23,46,57,67,82,93,90,88,89,93,123,155,185,226,246,244,222,180,167,136,91,69,86,
73,42,31,29,40,33,25,26,17,9,8,8,10,13,15,17,18,16,13,10,8,7,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,25,52,62,64,66,69,65,70,86,99,133,168,197,228,243,238,206,169,151,123,90,74,88,
67,36,29,24,23,24,22,18,8,3,2,3,5,6,7,7,8,7,5,4,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,30,61,65,61,56,51,51,55,82,107,141,178,204,223,236,229,205,172,144,115,96,81,89,
55,29,26,22,21,25,24,18,13,7,9,25,44,53,56,56,55,46,32,25,18,10,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,37,68,68,57,49,42,40,45,72,108,143,174,193,205,222,225,210,183,146,121,106,85,82,
50,24,23,21,21,23,22,17,16,16,31,74,110,127,135,135,124,99,80,64,50,31,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,42,69,63,47,37,32,31,32,55,94,133,162,177,190,208,214,208,183,148,124,106,81,72,
46,24,25,23,22,20,16,13,16,28,54,101,142,161,172,173,158,133,118,95,72,44,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,42,65,50,29,20,17,20,24,35,69,106,136,154,172,192,198,193,172,144,123,103,77,70,
45,22,22,22,18,15,13,9,13,35,73,120,157,178,195,197,186,172,147,115,85,51,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,48,70,50,28,16,13,10,14,29,46,70,100,121,148,168,174,166,150,134,113,92,72,68,
42,15,15,16,15,14,10,6,11,38,81,122,159,188,210,213,208,195,166,124,89,53,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,59,88,63,35,25,18,10,10,24,33,47,68,91,122,140,147,139,124,111,96,82,63,62,
40,11,9,10,12,11,7,4,11,37,74,113,155,192,216,222,218,202,171,132,92,54,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,55,91,67,41,26,17,9,5,11,20,26,38,69,93,113,124,118,106,91,80,73,55,59,
40,13,9,9,11,7,4,4,11,32,60,96,144,187,214,222,214,190,161,127,86,48,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,34,75,61,40,22,9,4,2,2,4,8,17,41,64,87,102,103,99,86,67,60,51,60,
42,15,13,11,14,17,14,9,14,36,75,114,160,190,207,219,208,180,148,114,77,42,10,0,0,0,1,1,1,0,1,3,6,6,5,3,2,2,1,0,1,14,46,42,29,11,0,1,2,2,2,4,11,21,32,56,72,72,79,80,67,56,46,59,
47,22,21,20,28,45,45,28,29,73,127,169,193,190,189,206,202,175,135,99,65,33,9,1,1,6,12,21,29,40,49,54,54,55,55,55,55,49,21,1,2,4,16,17,13,5,1,2,3,4,8,14,17,14,12,34,50,45,46,63,70,60,41,56,
53,33,36,34,50,77,87,68,72,130,179,213,224,206,192,202,189,158,115,75,44,23,7,1,5,16,31,50,73,99,118,124,120,118,118,111,105,96,55,10,2,1,3,6,5,1,1,2,5,9,20,31,35,23,16,28,38,35,34,55,69,63,46,62,
66,57,68,70,88,118,132,124,134,182,220,241,245,234,213,197,167,126,85,50,28,12,2,2,8,20,35,62,87,107,120,127,125,115,103,80,64,60,46,16,1,0,1,3,3,1,2,3,9,21,37,52,56,43,30,24,21,28,41,57,67,65,54,70,
81,80,100,119,135,153,167,179,187,199,225,242,244,234,208,178,133,85,54,32,16,3,0,2,9,20,34,56,75,83,92,100,100,87,67,44,29,26,21,10,2,0,0,0,2,3,3,6,9,18,37,56,60,45,30,15,9,20,30,45,55,57,47,67,
89,88,115,152,172,186,198,213,212,195,205,223,224,205,178,149,106,61,33,20,8,0,0,1,8,18,32,47,59,69,77,81,74,60,44,27,13,10,8,5,1,0,0,0,1,2,5,10,11,8,18,38,46,30,15,7,5,10,15,27,34,36,33,63,
91,100,134,169,195,212,227,240,236,212,198,201,185,159,136,112,82,44,20,13,3,0,0,1,7,16,25,33,44,57,66,66,57,44,31,16,6,2,2,3,2,0,0,0,0,3,12,21,22,13,7,18,29,20,5,3,3,5,7,13,16,18,25,64,
94,111,145,175,202,222,241,252,247,226,202,186,158,119,96,70,41,16,8,4,2,1,0,1,5,13,15,20,30,44,57,61,53,34,18,10,5,2,2,2,2,2,1,2,3,8,23,36,38,32,20,14,15,10,5,4,3,4,5,7,8,13,24,63,
91,103,133,165,190,216,238,249,244,227,206,177,143,105,79,34,7,0,0,0,2,2,0,1,2,7,9,16,33,50,65,67,55,37,26,13,5,4,3,2,2,2,3,4,6,11,25,43,49,42,29,16,6,6,6,5,5,6,8,6,8,18,26,59,
73,74,95,129,165,199,225,239,233,220,201,172,136,102,56,10,6,12,13,10,9,4,1,1,1,2,6,29,61,82,90,90,79,64,51,28,7,4,4,2,2,4,6,4,6,10,21,42,49,38,22,10,6,7,10,9,8,15,20,14,15,29,36,60,
55,49,67,100,139,177,205,223,223,209,188,161,125,89,34,2,16,33,32,23,14,9,2,0,1,3,17,54,90,112,119,120,108,91,73,45,23,13,7,3,2,5,6,3,6,10,20,38,46,33,14,7,7,8,12,14,16,25,28,27,28,33,38,59,
46,33,50,76,107,149,185,206,206,191,168,146,114,70,21,3,22,33,30,26,20,13,3,0,2,15,32,69,104,128,141,146,136,111,90,66,46,30,12,8,4,5,4,2,5,13,25,38,43,30,14,7,6,7,10,15,21,30,36,44,47,40,33,50,
40,27,40,56,84,125,161,181,181,160,138,118,94,55,13,5,23,30,27,27,22,12,4,0,2,20,36,62,97,129,157,169,159,129,105,89,69,44,21,14,13,10,5,2,3,9,24,37,37,25,13,11,10,11,15,22,28,47,57,63,62,46,29,46,
40,27,36,50,68,97,129,143,143,124,95,70,60,41,8,5,18,22,23,25,17,8,3,0,1,12,29,45,71,118,165,188,180,147,119,102,84,57,34,25,21,16,11,6,4,6,17,32,36,22,12,16,21,23,28,32,46,76,87,80,71,51,32,47,
37,22,30,42,56,71,74,66,56,46,32,24,20,16,4,3,6,6,11,16,12,6,2,0,0,7,23,29,48,107,165,198,199,171,136,115,92,62,42,32,25,20,15,9,8,11,18,33,38,30,25,32,42,51,62,62,81,107,108,94,73,49,31,45,
34,15,25,38,53,47,18,8,4,4,8,7,6,5,2,1,2,3,7,11,8,6,2,1,1,5,14,17,41,100,155,195,208,190,162,136,99,64,46,38,30,26,24,20,21,23,28,32,38,44,48,64,86,123,146,151,151,138,113,94,75,53,30,42,
37,16,31,51,63,48,9,23,41,45,43,36,32,18,2,1,4,9,7,5,5,4,3,2,2,6,21,31,44,72,130,181,205,199,181,155,118,81,60,42,35,36,34,36,35,32,29,29,36,52,63,79,125,200,219,223,221,190,128,94,77,61,36,44,
41,20,38,70,90,84,68,84,107,112,97,82,67,45,21,9,2,5,2,2,2,2,2,1,5,20,47,77,80,66,91,152,197,207,199,175,141,107,85,56,43,47,46,45,41,34,29,28,37,59,70,79,119,195,216,222,228,231,169,108,85,69,47,53,
50,36,59,100,126,126,116,127,134,136,134,121,95,68,50,36,12,3,2,14,23,25,22,16,25,52,85,131,144,119,108,147,185,201,197,182,163,134,113,88,62,56,53,49,44,37,34,31,36,53,64,67,80,112,133,153,182,220,201,132,105,82,55,62,
70,76,102,136,163,166,155,157,159,157,160,152,129,102,81,57,22,2,6,35,54,59,55,48,62,100,138,177,190,172,159,158,158,168,180,185,185,165,135,108,85,68,59,57,54,48,42,36,35,42,48,48,52,60,68,88,115,147,150,134,116,87,57,68,
84,101,133,164,193,204,195,184,182,187,182,172,151,122,99,64,24,2,24,62,72,69,73,84,107,146,171,187,193,183,167,146,128,135,161,179,179,160,128,102,94,76,73,73,66,56,50,44,41,40,41,41,41,44,59,74,88,100,115,123,112,83,56,72,
92,107,138,173,212,231,227,214,214,222,212,191,162,131,105,67,28,10,44,81,87,77,82,116,149,177,188,179,171,166,150,123,108,119,138,145,142,121,99,95,104,99,94,89,79,67,62,54,50,45,45,47,43,41,46,59,67,70,84,95,93,74,55,76,
93,106,140,180,219,242,247,241,238,240,229,199,166,135,101,70,50,47,67,90,97,95,108,145,172,184,182,165,147,136,118,90,87,95,104,107,99,84,85,100,112,116,112,100,92,86,75,65,57,50,48,51,50,45,43,51,55,54,63,73,76,65,54,77,
101,108,146,184,219,240,251,253,250,245,228,195,166,138,103,82,73,84,92,103,114,129,149,172,185,171,151,136,118,104,90,79,80,82,84,88,86,86,96,110,123,129,128,120,118,111,97,83,72,66,63,66,67,65,62,62,60,59,64,71,75,71,62,89,
};